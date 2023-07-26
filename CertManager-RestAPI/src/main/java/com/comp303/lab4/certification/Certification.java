package com.comp303.lab4.certification;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "certification")
public class Certification {

	@Id
    private String _id;
	@NotBlank(message="Certification code is required")
    private String certificationCode;
	@NotBlank(message="Certification name is required")
    private String certificationName;

	@NotBlank(message="Test format is required")
    private String format;
	@NotNull(message="Test duration is required")
	@Min(value = 1, message="Test duration must be greater than 0")
    private int duration;
	@NotNull(message="Number of questions is required")
	@Min(value = 1, message="Number of questions must be greater than 0")
    private int numberOfQuestions;
	@NotNull(message="Passing score is required")
	@DecimalMin(value="0.00", inclusive=true, message="Passing score must be a positive value")
    private Double passingScore;
	@NotNull(message="Test fee is required")
	@DecimalMin(value="0.00", inclusive=true, message="Test Fee must be a positive value")
    private Double fee;

    public Certification() {
        super();
    }

    public Certification(String _id, String certificationCode, String certificationName, String format, int duration,
            int numberOfQuestions, Double passingScore, Double fee) {
        super();
        this._id = _id;
        this.certificationCode = certificationCode;
        this.certificationName = certificationName;
        this.format = format;
        this.duration = duration;
        this.numberOfQuestions = numberOfQuestions;
        this.passingScore = passingScore;
        this.fee = fee;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(String certificationCode) {
        this.certificationCode = certificationCode;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Double getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Double passingScore) {
        this.passingScore = passingScore;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
