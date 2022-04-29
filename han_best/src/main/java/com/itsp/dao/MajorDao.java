package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.MajorVO;

public interface MajorDao {

	public String seqNextVal();
	
	public List<AttachFiles> selectFileList(String parentSeq);

	public AttachFiles selectFile(String seq);

	// major_about
	public Integer selectCountAbout(Map<String, Object> params);

	public List<MajorVO> selectListAbout(Map<String, Object> params);

	public MajorVO selectAbout(Map<String, Object> params);

	public void saveProcAbout(MajorVO MajorVO);

	public void deleteProcAbout(String seq);
	
	// major_intro
	public Integer selectCountIntro(Map<String, Object> params);

	public List<MajorVO> selectListIntro(Map<String, Object> params);

	public MajorVO selectIntro(Map<String, Object> params);

	public void saveProcIntro(MajorVO MajorVO);

	public void deleteProcIntro(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcIntro(ArrayList<HashMap> dataList) throws Exception;
	
	// major_goal
	public Integer selectCountGoal(Map<String, Object> params);

	public List<MajorVO> selectListGoal(Map<String, Object> params);

	public MajorVO selectGoal(Map<String, Object> params);

	public void saveProcGoal(MajorVO MajorVO);

	public void deleteProcGoal(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcGoal(ArrayList<HashMap> dataList) throws Exception;
	
	// major_Field
	public Integer selectCountField(Map<String, Object> params);

	public List<MajorVO> selectListField(Map<String, Object> params);

	public MajorVO selectField(Map<String, Object> params);

	public void saveProcField(MajorVO MajorVO);

	public void deleteProcField(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcField(ArrayList<HashMap> dataList) throws Exception;
	
	// major_curriculum
	public Integer selectCountCurriculum(Map<String, Object> params);

	public List<MajorVO> selectListCurriculum(Map<String, Object> params);

	public MajorVO selectCurriculum(Map<String, Object> params);

	public void saveProcCurriculum(MajorVO MajorVO);

	public void deleteProcCurriculum(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcCurriculum(ArrayList<HashMap> dataList) throws Exception;
	
	// major_course
	public Integer selectCountCourse(Map<String, Object> params);

	public List<MajorVO> selectListCourse(Map<String, Object> params);

	public MajorVO selectCourse(Map<String, Object> params);

	public void saveProcCourse(MajorVO MajorVO);

	public void deleteProcCourse(String seq);
	
	// Facilities
	public Integer selectCountFacilities(Map<String, Object> params);

	public List<MajorVO> selectListFacilities(Map<String, Object> params);

	public MajorVO selectFacilities(Map<String, Object> params);

	public void saveProcFacilities(MajorVO MajorVO);

	public void deleteProcFacilities(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcFacilities(ArrayList<HashMap> dataList) throws Exception;
	
	// Professor
	public Integer selectCountProfessor(Map<String, Object> params);

	public List<MajorVO> selectListProfessor(Map<String, Object> params);

	public MajorVO selectProfessor(Map<String, Object> params);

	public void saveProcProfessor(MajorVO MajorVO);

	public void deleteProcProfessor(String seq);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcProfessor(ArrayList<HashMap> dataList) throws Exception;
	
	// Application
	public Integer selectCountApplication(Map<String, Object> params);

	public MajorVO selectApplication(Map<String, Object> params);

	public void saveProcApplication(MajorVO MajorVO);

	public void deleteProcApplication(String seq);
		
}
