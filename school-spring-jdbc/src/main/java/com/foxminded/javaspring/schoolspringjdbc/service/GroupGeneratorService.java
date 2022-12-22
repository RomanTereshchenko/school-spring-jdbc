package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.model.Group;

@Service
public class GroupGeneratorService {

	private Random random = new Random();
	
	public List<Group> generateNGroups(int countToGenerate) {

		List<Group> groupsLocal = new ArrayList<>();
		
		for (int i = 0; i < countToGenerate; i++) {
			Group group = new Group();
			group.setGroupID(i+1);
			group.setGroupName(generateGroupName());
			groupsLocal.add(group);
		}
		System.out.println("Groups generated");
		return groupsLocal;
	}

	private String generateGroupName() {
		return (new StringBuilder().append(generateRandomChar())
				.append(generateRandomChar()) + "-" + generateRandomInt() + generateRandomInt())
				.toString();
	}

	private int generateRandomInt() {
		return random.nextInt(10);
	}

	private char generateRandomChar() {
		return (char) (random.nextInt(26) + 'a');
	}

}
