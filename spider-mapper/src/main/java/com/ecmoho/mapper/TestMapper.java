package com.ecmoho.mapper;

import com.ecmoho.common.persistent.BaseMapper;
import com.ecmoho.models.TestVo;

public interface TestMapper extends BaseMapper{
	
	 int insertTest(TestVo vo);
}
