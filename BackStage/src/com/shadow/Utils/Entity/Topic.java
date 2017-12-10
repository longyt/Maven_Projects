package com.shadow.Utils.Entity;

public class Topic extends BaseEntity{
	
	 	private Integer tid;
	    private String question;
	    private String aa;
	    private String ab;
	    private String ac;
	    private String ad;
	    private String ra;
	    private String comment;
	    
	    public Topic(){}

	    
	    
		public Topic(Integer tid, String question, String aa, String ab, String ac, String ad, String ra,
				String comment) {
			super();
			this.tid = tid;
			this.question = question;
			this.aa = aa;
			this.ab = ab;
			this.ac = ac;
			this.ad = ad;
			this.ra = ra;
			this.comment = comment;
		}



		public Integer getTid() {
			return tid;
		}

		public void setTid(Integer tid) {
			this.tid = tid;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public String getAa() {
			return aa;
		}

		public void setAa(String aa) {
			this.aa = aa;
		}

		public String getAb() {
			return ab;
		}

		public void setAb(String ab) {
			this.ab = ab;
		}

		public String getAc() {
			return ac;
		}

		public void setAc(String ac) {
			this.ac = ac;
		}

		public String getAd() {
			return ad;
		}

		public void setAd(String ad) {
			this.ad = ad;
		}

		public String getRa() {
			return ra;
		}

		public void setRa(String ra) {
			this.ra = ra;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		@Override
		public String toString() {
			return "Topic [tid=" + tid + ", question=" + question + ", aa=" + aa + ", ab=" + ab + ", ac=" + ac + ", ad="
					+ ad + ", ra=" + ra + ", comment=" + comment + "]";
		}
		
	    
	    

}
