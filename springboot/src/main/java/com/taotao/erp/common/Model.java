package com.taotao.erp.common;

/**
 * @author liuzeke
 * @version 1.0
 */
public class Model {

	private String _id;
	private Long sequence;

	public String get_id() {
		return _id;
	}

	public Model set_id(String _id) {
		this._id = _id;
		return this;
	}

	public Long getSequence() {
		return sequence;
	}

	public Model setSequence(Long sequence) {
		this.sequence = sequence;
		return this;
	}
}
