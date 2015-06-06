<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xmi="http://schema.omg.org/spec/XMI/2.1"
	xmlns:str="http://exslt.org/strings"
	xmlns:xsltsl-str="http://xsltsl.org/string" 
	xmlns:mylib="http://mylib"
	xmlns:lambda="http://opendsml.org/master/basic/simpleworld.v0"
	extension-element-prefixes="xsltsl-str str mylib">
	<xsl:template match="lambda:value0">
		<xsl:variable name="r1">
			<xsl:call-template name="mylib:normalizeElementName">
				<xsl:with-param name="stringIn" select="." />
			</xsl:call-template>
		</xsl:variable>
		<xsl:attribute name="name">
         	<xsl:call-template name="mylib:rewriteName">
				<xsl:with-param name="stringIn" select="$r1" />
				<xsl:with-param name="market" select="'value1'"/>
			</xsl:call-template>
        </xsl:attribute>
	</xsl:template>
</xsl:stylesheet>

