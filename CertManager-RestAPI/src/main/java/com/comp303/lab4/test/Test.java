package com.comp303.lab4.test;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.comp303.lab4.candidate.Candidate;
import com.comp303.lab4.certification.Certification;
import com.comp303.lab4.testCentre.TestCentre;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "test")
public class Test {
	
	@Id
	private String _id;
	
	@NotNull(message="Candidate is required")
	private Candidate candidate;
	@NotNull(message="Certification is required")
	private Certification certification;	
	@NotNull(message="TestCentre is required")
	private TestCentre testCentre;
	@NotNull(message="Test Datetime is required")
	private LocalDateTime testDateTime;
	private LocalDateTime testEndDateTime;
	
	@DecimalMin(value="0.00", inclusive=true, message="Score must be a positive value")
	private double score; 
	private String result;
	
//	//Reserved/Rescheduled/Completed
//	@NotBlank(message="Status is required")
	private String status;
	
	public Test() {
		super();
	}
	
	public Test(String _id, Candidate candidate, Certification certification, TestCentre testCentre,
			LocalDateTime testDateTime, LocalDateTime testEndDateTime, double score, String result, String status) {
		super();
		this._id = _id;
		this.candidate = candidate;
		this.certification = certification;
		this.testCentre = testCentre;
		this.testDateTime = testDateTime;
		this.testEndDateTime = testEndDateTime;
		this.score = score;
		this.result = result;
		this.status = status;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
	}

	public TestCentre getTestCentre() {
		return testCentre;
	}

	public void setTestCentre(TestCentre testCentre) {
		this.testCentre = testCentre;
	}

	public LocalDateTime getTestDateTime() {
		return testDateTime;
	}

	public void setTestDateTime(LocalDateTime testDateTime) {
		this.testDateTime = testDateTime;
	}

	public LocalDateTime getTestEndDateTime() {
		return testEndDateTime;
	}

	public void setTestEndDateTime(LocalDateTime testEndDateTime) {
		this.testEndDateTime = testEndDateTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
