package  com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;




@Entity 
@Table(name = "template")
public class TemplateEntity extends Auditable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMPLATE_SQ")
    @SequenceGenerator(sequenceName = "SEQ_TEMPLATE", allocationSize = 1, name = "TEMPLATE_SQ")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "template_id_en")
	private String templateIdEn;
	
	@Column(name = "template_id_hi")
	private String templateIdHi;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "message_text_en")
	private String messageTextEn;
	
	@Column(name = "message_text_hi")
	private String messageTextHi;
	
	@Column(name = "subject_en")
	private String subjectEn;
	
	@Column(name = "subject_hi")
	private String subjectHi;
	
	@Column(name = "key")
	private String key;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessageTextEn() {
		return messageTextEn;
	}

	public void setMessageTextEn(String messageTextEn) {
		this.messageTextEn = messageTextEn;
	}

	public String getMessageTextHi() {
		return messageTextHi;
	}

	public void setMessageTextHi(String messageTextHi) {
		this.messageTextHi = messageTextHi;
	}

	public String getSubjectEn() {
		return subjectEn;
	}

	public void setSubjectEn(String subjectEn) {
		this.subjectEn = subjectEn;
	}

	public String getSubjectHi() {
		return subjectHi;
	}

	public void setSubjectHi(String subjectHi) {
		this.subjectHi = subjectHi;
	}

	public String getTemplateIdEn() {
		return templateIdEn;
	}

	public void setTemplateIdEn(String templateIdEn) {
		this.templateIdEn = templateIdEn;
	}

	public String getTemplateIdHi() {
		return templateIdHi;
	}

	public void setTemplateIdHi(String templateIdHi) {
		this.templateIdHi = templateIdHi;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
