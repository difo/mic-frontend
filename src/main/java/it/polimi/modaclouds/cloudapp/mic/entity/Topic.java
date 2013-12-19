package it.polimi.modaclouds.cloudapp.mic.entity;

/*
 * *****************************
 * mic
 * *****************************
 * Copyright (C) 2013 deib-polimi
 * *****************************
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************
 */


import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "Topic")
public class Topic {
	
	@Id
	private String topicName;
	
	private ArrayList<String> topicQuestions;
	
	public Topic() {
		
	}
	
	public Topic(String topicName, ArrayList<String> topicQuestions) {
		super();
		this.topicName = topicName;
		this.topicQuestions = topicQuestions;
	}
	
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public ArrayList<String> getTopicQuestions() {
		return topicQuestions;
	}
	public void setTopicQuestions(ArrayList<String> topicQuestions) {
		this.topicQuestions = topicQuestions;
	}
	
}
