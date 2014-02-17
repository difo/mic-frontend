/**
 * Copyright 2013 deib-polimi
 * Contact: deib-polimi <marco.miglierina@polimi.it>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.polimi.modaclouds.cloudapp.mic.entity;





import java.io.Serializable;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.NamedQuery;
import javax.persistence.Table;




@SuppressWarnings("serial")

@Entity
@Table(name = "UserRatings")

@NamedQuery(name="UserRatingsQuery",query="SELECT u FROM UserRatings u")

public class UserRatings implements Serializable{



	@Id
	private String id;
	
	private String email;
	
	private String topicName;

	
	private List<String> ratings;
	
	

	//private Map<Integer, Integer> ratings;

	private String todelete;



	public UserRatings(String email, String topicName, List<String> ratings)

	{

		super();

		this.id=email+"_"+topicName;

		this.email=email;

		this.topicName=topicName;

		this.ratings=ratings;

		this.todelete=new String("true");

		

	}

	

	public UserRatings(){}

	

	public String getId() {

		return id;

	}

	public void setId(String id) {

		this.id = id;

	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getTopicName() {

		return topicName;

	}

	public void setTopicName(String topicName) {

		this.topicName = topicName;

	}

	public List<String> getRatings() {

		return ratings;

	}

	public void setRatings(List<String> ratings) {

		this.ratings = ratings;

	}

	public String getTodelete() {

		return todelete;

	}

	public void setTodelete(String todelete) {

		this.todelete = todelete;

	}

	

}

