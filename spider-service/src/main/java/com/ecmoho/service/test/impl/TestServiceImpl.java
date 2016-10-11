package com.ecmoho.service.test.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecmoho.mapper.TestMapper;
import com.ecmoho.models.TestVo;
import com.ecmoho.service.test.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	public TestMapper testMapper;
	
	@Override
	public int insertTest(TestVo vo) {
		
		return testMapper.insertTest(vo);
	}

}
