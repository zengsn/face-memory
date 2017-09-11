package top.it138.face.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.it138.face.Recognize.RecognitionUnit;
import top.it138.face.dto.RecognitionData;

@Service
public class RecognitionServiceImpl implements RecognitionService{
	@Autowired
	private RecognitionUnit recognitionUnit;
	
	@Override
	public Double recognize(RecognitionData data) {
		return recognitionUnit.recognize(data);
	}
	
}
