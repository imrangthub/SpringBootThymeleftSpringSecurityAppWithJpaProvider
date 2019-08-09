package com.madbarsoft.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.madbarsoft.user.UserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "roles_tbl")
public class RoleEntity  implements Serializable {


	private static final long serialVersionUID = 7445912094988420567L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
	private String name;

	private String description;
	
	
    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;



	
}
