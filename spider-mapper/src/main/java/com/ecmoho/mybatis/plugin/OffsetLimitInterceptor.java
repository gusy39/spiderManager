package com.ecmoho.mybatis.plugin;


import com.ecmoho.common.dto.Pager;
import com.ecmoho.common.util.PropertiesHelper;
import com.ecmoho.mybatis.dialect.Dialect;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * <strong>Title : OffsetLimitInterceptor<br>
 * </strong> <strong>Description : </strong> 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 将拦截Executor.query()方法实现分页方言的插入. 配置文件内容:
 * 
 * <pre>
 * 	&lt;plugins>
 * 	&lt;com.zban.mybatis.com.sqbase.mybatis.dialect.plugin interceptor="cn.org.rapid_framework.ibatis3.com.zban.mybatis.com.sqbase.mybatis.dialect.plugin.OffsetLimitInterceptor">
 * 		&lt;property name="dialectClass" value="cn.org.rapid_framework.jdbc.com.sqbase.mybatis.dialect.MySQLDialect"/>
 * 	&lt;/com.zban.mybatis.com.sqbase.mybatis.dialect.plugin>
 * &lt;/plugins>
 * </pre>
 * 
 * <strong>Create on : 2011-11-2<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br>
 * </strong>
 * <p>
 * 
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {
	public final Log logger = LogFactory.getLog(getClass());

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

	private Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	public void processIntercept(final Object[] queryArgs) throws Throwable {
		// queryArgs = query(MappedStatement ms, Object parameter, RowBounds
		// rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];

		if (rowBounds != null && rowBounds instanceof Pager) {
			int offset = rowBounds.getOffset();
			int limit = rowBounds.getLimit();
			Pager<?> pager = (Pager<?>) rowBounds;
			if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
				BoundSql boundSql = ms.getBoundSql(parameter);
				String sql = boundSql.getSql().trim();

				Integer cache = pager.getTotalCount();
				int totpage = 0;
				if (cache == null || cache < 1) {

					StringBuffer countSql = new StringBuffer(sql.length() + 100);
					String sqlUpper = sql.toUpperCase();
					int select = sqlUpper.indexOf("SELECT");
					int from = sqlUpper.indexOf("FROM");
//					if (select >= 0 && from > 0) {
//						countSql.append(sql);
//						countSql.replace(select + 6, from, " count(1) ");
//					} else {
//						countSql.append("select count(1) from (").append(sql).append(") cTab");
//					}
                    countSql.append("select count(1) from (").append(sql).append(") cTab");
					logger.debug("page Preparing：" + countSql.toString().replaceAll("\n|\n\r|\t", ""));
					Connection connection = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
					PreparedStatement countStmt = connection.prepareStatement(countSql.toString());
					BoundSql countBS = new BoundSql(ms.getConfiguration(), countSql.toString(), boundSql.getParameterMappings(), boundSql.getParameterObject());
					setParameters(countStmt, ms, countBS, boundSql.getParameterObject());
					ResultSet rs = countStmt.executeQuery();
					if (rs.next()) {
						totpage = rs.getInt(1);
					}
					connection.close();
				} else {
					logger.info("分页总记录存在缓存");
					totpage = cache;
				}
				pager.setTotalCount(totpage);

				if (dialect.supportsLimitOffset()) {
					sql = dialect.getLimitString(sql, offset, limit);
					offset = RowBounds.NO_ROW_OFFSET;
				} else {
					sql = dialect.getLimitString(sql, 0, limit);
				}
				limit = RowBounds.NO_ROW_LIMIT;

				queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
				BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
				MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
				queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}

					TypeHandler<Object> typeHandler = (TypeHandler<Object>) parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = new PropertiesHelper(properties).getRequiredString("dialectClass");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("cannot create com.sqbase.mybatis.dialect instance by dialectClass:" + dialectClass, e);
		}
		System.out.println(OffsetLimitInterceptor.class.getSimpleName() + ".com.sqbase.mybatis.dialect=" + dialectClass);
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

}
