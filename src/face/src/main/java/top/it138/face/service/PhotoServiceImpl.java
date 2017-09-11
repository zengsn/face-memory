package top.it138.face.service;

import java.util.List;

import org.springframework.stereotype.Service;

import top.it138.face.entity.Photo;

@Service
public class PhotoServiceImpl extends BaseServiceImpl<Photo> implements PhotoService{

	@Override
	public List<Photo> selectByPersonId(Long personId) {
		Photo photo = new Photo();
		photo.setPersonId(personId);
		return mapper.select(photo);
	}

}
