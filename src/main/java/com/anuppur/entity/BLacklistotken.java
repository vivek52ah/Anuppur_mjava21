package com.anuppur.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "blacklistertokens")
public class BLacklistotken {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Basic(optional = false)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String token;

	    @Column(nullable = false)
	    private String blacklistedAt;
	    
	    @Column
	    private LocalDateTime  expirytime;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getBlacklistedAt() {
			return blacklistedAt;
		}

		public void setBlacklistedAt(String blacklistedAt) {
			this.blacklistedAt = blacklistedAt;
		}

		public LocalDateTime getExpirytime() {
			return expirytime;
		}

		public void setExpirytime(LocalDateTime expirytime) {
			this.expirytime = expirytime;
		}
	    
	    
	    

}
