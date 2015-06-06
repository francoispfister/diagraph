<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:mylib="http://mylib"
	xmlns:xsltsl-str="http://xsltsl.org/string" xmlns:str="http://exslt.org/strings"
	extension-element-prefixes="mylib xsltsl-str str">



	<xsl:import href="../lib/xsltsl-1.2.1/string.xsl" />
	<xsl:import href="../lib/exsltlib/str/str.xsl" />
	<!-- space , carriage returns, line feeds,  tabs, apos, and so on  -->
	<xsl:variable name="str-ws" select="'&#x20;&#x9;&#xD;&#xA;'" />
	<xsl:variable name="apos" select='"&apos;"' />
	<xsl:variable name="onespace" select="' '" />
	<xsl:variable name="mylib-str-ws" select="concat($str-ws,$apos,'/','-','(',')')" />



	<xsl:template name="mylib:replaceCharsInString">
		<xsl:param name="stringIn" />
		<xsl:param name="charsIn" />
		<xsl:param name="charsOut" />
		<xsl:param name="market" />
		<xsl:choose>
			<xsl:when test="contains($stringIn,$charsIn)">
				<xsl:value-of
					select="concat(substring-before($stringIn,$charsIn),$charsOut)" />
				<xsl:call-template name="mylib:replaceCharsInString">
					<xsl:with-param name="stringIn"
						select="substring-after($stringIn,$charsIn)" />
					<xsl:with-param name="charsIn" select="$charsIn" />
					<xsl:with-param name="charsOut" select="$charsOut" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$stringIn" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>




	<xsl:template name="mylib:camelize">
		<xsl:param name="text" />
		<!-- First change all 'strange' characters to spaces -->
		<xsl:param name="string-with-only-spaces">
			<xsl:value-of select="translate($text,$mylib-str-ws,'     ')" />
		</xsl:param>
		<!-- Then process them -->
		<xsl:param name="before-space-removal">
			<xsl:variable name="firstword">
				<xsl:choose>
					<xsl:when test="contains($string-with-only-spaces,$onespace)">
						<xsl:value-of
							select="substring-before($string-with-only-spaces, $onespace)" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$string-with-only-spaces" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:call-template name="xsltsl-str:to-lower">
				<xsl:with-param name="text" select="substring($firstword, 1, 1)" />
			</xsl:call-template>
			<xsl:value-of select="substring($firstword, 2)"></xsl:value-of>
			<xsl:call-template name="xsltsl-str:capitalise">
				<xsl:with-param name="text">
					<xsl:value-of
						select="substring($string-with-only-spaces, string-length($firstword) + 2)" />
				</xsl:with-param>
				<xsl:with-param name="all" select="true()" />
			</xsl:call-template>
		</xsl:param>
		<xsl:value-of select="translate($before-space-removal,' ','')" />
	</xsl:template>




	<xsl:template name="mylib:rewriteName">
		<xsl:param name="stringIn" />
		<xsl:param name="market" />
		<xsl:variable name="r1">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$stringIn" />
				<xsl:with-param name="charsIn" select="'Intrantdecontrole'" />
				<xsl:with-param name="charsOut" select="'IntrantDeControle'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="r2">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$r1" />
				<xsl:with-param name="charsIn" select="'Intrantderessource'" />
				<xsl:with-param name="charsOut" select="'IntrantDeRessource'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="r3">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$r2" />
				<xsl:with-param name="charsIn" select="'Intrantdeprecondition'" />
				<xsl:with-param name="charsOut" select="'IntrantDePreCondition'" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="r4">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$r3" />
				<xsl:with-param name="charsIn" select="'Portentreesortieuffram'" />
				<xsl:with-param name="charsOut" select="'PortEntreeSortieUFFram'" />
			</xsl:call-template>
		</xsl:variable>
			<xsl:variable name="r5">
			<xsl:value-of select="concat($r4,$market)" />
		</xsl:variable>

		<xsl:value-of select="$r5" />
	</xsl:template>




	<xsl:template name="mylib:normalizeAttributeName">
		<xsl:param name="stringIn" />

		<xsl:variable name="normalizedName0">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$stringIn" />
				<xsl:with-param name="charsIn" select="'\u00e9'" />
				<xsl:with-param name="charsOut" select="'e'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName1">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName0" />
				<xsl:with-param name="charsIn" select="'\u00e0'" />
				<xsl:with-param name="charsOut" select="'a'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName2">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName1" />
				<xsl:with-param name="charsIn" select="'\u00f4'" />
				<xsl:with-param name="charsOut" select="'o'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName3">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName2" />
				<xsl:with-param name="charsIn" select="'\u00ea'" />
				<xsl:with-param name="charsOut" select="'e'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName4">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName3" />
				<xsl:with-param name="charsIn" select="'\u00e7'" />
				<xsl:with-param name="charsOut" select="'c'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName4" />
				<xsl:with-param name="charsIn" select="'\u00e8'" />
				<xsl:with-param name="charsOut" select="'e'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="camelizedName">
			<xsl:call-template name="mylib:camelize">
				<xsl:with-param name="text">
					<xsl:value-of select="$normalizedName" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>

		<xsl:value-of select="$camelizedName" />

	</xsl:template>








	<xsl:template name="mylib:normalizeElementName">
		<xsl:param name="stringIn" />

		<xsl:variable name="normalizedName0">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$stringIn" />
				<xsl:with-param name="charsIn" select="'\u00e9'" />
				<xsl:with-param name="charsOut" select="'e'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName1">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName0" />
				<xsl:with-param name="charsIn" select="'\u00e0'" />
				<xsl:with-param name="charsOut" select="'a'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName2">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName1" />
				<xsl:with-param name="charsIn" select="'\u00f4'" />
				<xsl:with-param name="charsOut" select="'o'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="normalizedName">
			<xsl:call-template name="mylib:replaceCharsInString">
				<xsl:with-param name="stringIn" select="$normalizedName2" />
				<xsl:with-param name="charsIn" select="'\u00e8'" />
				<xsl:with-param name="charsOut" select="'e'" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="trimmedName1">
			<xsl:value-of select="translate($normalizedName, '(', ' ')" />
		</xsl:variable>

		<xsl:variable name="trimmedName2">
			<xsl:value-of select="translate($trimmedName1, ')', ' ')" />
		</xsl:variable>

		<xsl:variable name="trimmedName3">
			<xsl:value-of select='translate($trimmedName2, "&apos;", " ")' />
		</xsl:variable>

		<xsl:variable name="capitalizedName">
			<xsl:call-template name="xsltsl-str:to-camelcase">
				<xsl:with-param name="text">
					<xsl:value-of select="$trimmedName3" />
				</xsl:with-param>
				<xsl:with-param name="upper" select="true()" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="trimmedName4">
			<xsl:value-of select="translate($capitalizedName, ' ', '')" />
		</xsl:variable>

		<xsl:value-of select="$trimmedName4" />

	</xsl:template>

</xsl:stylesheet>
