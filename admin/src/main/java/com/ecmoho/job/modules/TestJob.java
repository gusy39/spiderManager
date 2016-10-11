package com.ecmoho.job.modules;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecmoho.service.test.TestService;

public class TestJob extends com.bstek.bdf2.core.orm.jdbc.JdbcDao implements Job{

	@Autowired(required=false)
	private TestService testService;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {

//		TestVo vo = new TestVo();
//		System.out.println(testService.insertTest(vo) + "aaaaa");
		this.getJdbcTemplate("hsql").execute("INSERT INTO test(aa, bb) VALUES ('aa', 'aa')");
		this.getJdbcTemplate("hsql2").execute("INSERT INTO test(aa, bb) VALUES ('aa', 'aa');");
		List<Map<String,Object>> list = this.getJdbcTemplate("hsql").queryForList("select * from test");
		List<Map<String,Object>> list2 = this.getJdbcTemplate("hsql2").queryForList("select * from test");
		System.out.println( "aaaaa");
	}
}