package com.springrest.springrest.services;

/**
*@author kshitij
*/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.springrest.dao.CourseDao;
import com.springrest.springrest.entities.Course;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseDao courseDao;
	
	public CourseServiceImpl() {
		// TODO Auto-generated constructor stub

	}
	
	@Override
	public List<Course> getCourses() {
				return courseDao.findAll();
	}
	
	@Override
	public Course getCourse(long courseId) {

		return courseDao.findById(courseId).get();
	}
	
	@Override
	public Course addCourse(Course course) {
		courseDao.save(course);
		return course;
	}

	@Override
	public Course updateCourse(Course course) {
		
		courseDao.save(course);
		
		return course;
	}

	@Override
	public void deleteCourse(long parseLong) {
	
//		list = this.list.stream().filter(e -> e.getId()!=parseLong).collect(Collectors.toList());
		Course entity = courseDao.findById(parseLong).get();
		courseDao.delete(entity);
	}
	

}
