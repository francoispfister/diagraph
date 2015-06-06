<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
	xmlns:uml="http://schema.omg.org/spec/UML/2.0" xmlns:str="http://exslt.org/strings"
	xmlns:xsltsl-str="http://xsltsl.org/string" xmlns:mylib="http://mylib"
	extension-element-prefixes="xsltsl-str str mylib">

<!-- attention paramétrer transformer dans options orangevolt  -->

	<xsl:import href="../lib/xsltsl-1.2.1/string.xsl" />
	<xsl:import href="../lib/exsltlib/str/str.xsl" />
	<xsl:import href="mylib.xsl" />
	 <!--xsl:namespace name="x" select="'someNamespace'"/-->
	 <!--xsl:include href="namespaces.xsl" /-->
	 <!-- 	xmlns:lambda="http://opendsml.org/master/basic/simpleworld.v0" -->
	 

	<xsl:output method="xml" encoding="UTF-8" />
    <xsl:param name="supermarketname" />
    

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>


    <xsl:include href="var.xsl"/>


	<xsl:template match="uml:Model/ownedMember/@name">
		<xsl:variable name="r1">
			<xsl:call-template name="mylib:normalizeElementName">
				<xsl:with-param name="stringIn" select="." />
			</xsl:call-template>
		</xsl:variable>
		<xsl:attribute name="name">
         	<xsl:call-template name="mylib:rewriteName">
				<xsl:with-param name="stringIn" select="$r1" />
				<xsl:with-param name="market" select="$supermarketname"/>
				
			</xsl:call-template>
        </xsl:attribute>
	</xsl:template>
	
	
	
	<xsl:template match="uml:Model/ownedMember/ownedAttribute/@name|uml:Model/ownedMember/ownedEnd/@name">
		<xsl:attribute name="name">
         <xsl:call-template name="mylib:normalizeAttributeName">
				<xsl:with-param name="stringIn" select="." />
			</xsl:call-template>
        </xsl:attribute>
	</xsl:template>	
	



</xsl:stylesheet>