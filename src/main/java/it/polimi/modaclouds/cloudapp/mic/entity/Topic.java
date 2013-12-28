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


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "Topic")
public class Topic {
	
	@Id
	/*
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="topicName")
	*/
	private String topicName;
	
	private List<String> topicQuestions;
	
	public Topic() {
		
	}
	
	public Topic(String topicName, List<String> topicQuestions) {
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
	public List<String> getTopicQuestions() {
		return topicQuestions;
	}
	public void setTopicQuestions(List<String> topicQuestions) {
		this.topicQuestions = topicQuestions;
	}
	
}
