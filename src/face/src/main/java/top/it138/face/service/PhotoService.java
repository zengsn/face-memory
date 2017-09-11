package top.it138.face.service;

import java.util.List;

import top.it138.face.entity.Photo;

public interface PhotoService extends BaseService<Photo>{
	List<Photo> selectByPersonId(Long personId);
}
