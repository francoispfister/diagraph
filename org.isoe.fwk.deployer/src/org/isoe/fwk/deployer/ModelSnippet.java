package org.isoe.fwk.deployer;

public class ModelSnippet{

	private String basePackage;
	private String[] itfs={"A","B"};
	private String[] clazs={"AImpl","BImpl"};
	private String aPackage = "x.y";
	private String anotherPackage = "m.n";
	private String itf2="D";
	private String claz2="DImpl";


	public String getBasePackage() {
		return basePackage;
	}

	public String[] getItfs() {
		return itfs;
	}

	public String[] getClazs() {
		return clazs;
	}

	public String getaPackage() {
		return basePackage+"."+aPackage;
	}

	public String getAnotherPackage() {
		return basePackage+"."+anotherPackage;
	}

	public String getItf2() {
		return itf2;
	}

	public String getClaz2() {
		return claz2;
	}

	private  ModelSnippet(){

	}

	public ModelSnippet(String basePackage) {
		this.basePackage=basePackage;
	}


}
