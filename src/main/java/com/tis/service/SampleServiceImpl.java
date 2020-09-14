package com.tis.service;
import javax.inject.Inject;

//����Ͻ� ���� =>@Service
import org.springframework.stereotype.Service;

import com.tis.persistence.SampleDao;

@Service("sampleServiceImpl")
public class SampleServiceImpl implements SampleService{

	@Inject
	private SampleDao sDao;
	
	@Override
	public int getTableCount() {
		// TODO Auto-generated method stub
		return sDao.getTotalCount();
	}

}
