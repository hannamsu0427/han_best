package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.MajorVO;

public class MajorImp extends SqlMapClientDaoSupport implements MajorDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachFiles> selectFileList(String parentSeq) {
		return getSqlMapClientTemplate().queryForList("attachFile.selectDataList", parentSeq);
	}

	@Override
	public AttachFiles selectFile(String seq) {
		return (AttachFiles) getSqlMapClientTemplate().queryForObject("attachFile.selectData", seq);
	}

	/*
	 * major_about
	 */
	@Override
	public Integer selectCountAbout(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorAbout.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListAbout(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorAbout.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectAbout(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorAbout.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcAbout(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorAbout.saveProcData", MajorVO);
	}

	public void deleteProcAbout(String seq) {
		getSqlMapClientTemplate().delete("majorAbout.deletePrcoData", seq);
	}

	/*
	 * intro
	 */
	@Override
	public Integer selectCountIntro(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorIntro.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListIntro(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorIntro.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectIntro(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorIntro.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveProcIntro(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorIntro.saveProcData", MajorVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void deleteProcIntro(String seq) {
		getSqlMapClientTemplate().delete("majorIntro.deletePrcoData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcIntro(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorIntro.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Goal
	 */
	@Override
	public Integer selectCountGoal(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorGoal.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListGoal(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorGoal.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectGoal(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorGoal.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveProcGoal(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorGoal.saveProcData", MajorVO);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void deleteProcGoal(String seq) {
		getSqlMapClientTemplate().delete("majorGoal.deletePrcoData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcGoal(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorGoal.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Field
	 */
	@Override
	public Integer selectCountField(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorField.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListField(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorField.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectField(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorField.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcField(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorField.saveProcData", MajorVO);
	}

	public void deleteProcField(String seq) {
		getSqlMapClientTemplate().delete("majorField.deletePrcoData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcField(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorField.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Curriculum
	 */
	@Override
	public Integer selectCountCurriculum(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorCurriculum.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListCurriculum(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorCurriculum.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectCurriculum(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorCurriculum.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcCurriculum(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorCurriculum.saveProcData", MajorVO);
	}

	public void deleteProcCurriculum(String seq) {
		getSqlMapClientTemplate().delete("majorCurriculum.deletePrcoData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcCurriculum(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorCurriculum.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Course
	 */
	@Override
	public Integer selectCountCourse(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorCourse.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListCourse(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorCourse.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectCourse(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorCourse.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcCourse(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorCourse.saveProcData", MajorVO);
	}

	public void deleteProcCourse(String seq) {
		getSqlMapClientTemplate().delete("majorCourse.deletePrcoData", seq);
	}

	/*
	 * Facilities
	 */
	@Override
	public Integer selectCountFacilities(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorFacilities.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListFacilities(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorFacilities.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectFacilities(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorFacilities.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcFacilities(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorFacilities.saveProcData", MajorVO);
	}

	public void deleteProcFacilities(String seq) {
		getSqlMapClientTemplate().delete("majorFacilities.deleteProcData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcFacilities(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorFacilities.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Professor
	 */
	@Override
	public Integer selectCountProfessor(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorProfessor.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MajorVO> selectListProfessor(Map<String, Object> params) {
		List<MajorVO> voList = getSqlMapClientTemplate().queryForList("majorProfessor.selectDataList", params);
		List<MajorVO> resultList = new ArrayList<MajorVO>();

		for (MajorVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public MajorVO selectProfessor(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorProfessor.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcProfessor(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorProfessor.saveProcData", MajorVO);
	}

	public void deleteProcProfessor(String seq) {
		getSqlMapClientTemplate().delete("majorProfessor.deleteProcData", seq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcProfessor(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("majorProfessor.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * Application
	 */
	@Override
	public Integer selectCountApplication(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("majorApplication.selectCount", params);
	}

	@Override
	public MajorVO selectApplication(Map<String, Object> params) {
		MajorVO vo = (MajorVO) getSqlMapClientTemplate().queryForObject("majorApplication.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	public void saveProcApplication(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("majorApplication.saveProcData", MajorVO);
	}

	public void deleteProcApplication(String seq) {
		getSqlMapClientTemplate().delete("v.deletePrcoData", seq);
	}
}
