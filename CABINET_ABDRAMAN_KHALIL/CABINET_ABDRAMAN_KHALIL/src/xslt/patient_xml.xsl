<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : patient.xsl
    Created on : 12 novembre 2023, 16:48
    Author     : Khalil
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:cab="http://www.ujf-grenoble.fr/l3miage/medical" 
                xmlns:act="http://www.ujf-grenoble.fr/l3miage/actes" version="1.0">
    
    <!-- fichier généré de type xml -->
    <xsl:output indent="yes" method="xml"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <!-- définition d'un Paramètre global 'destinedName' servant à affichier une page dédiée à un patient en particulier 
        grâce à sa valeur qui est le nom du patient en question
    -->
    <xsl:param name="destinedName">Alécole</xsl:param>
    
    <!-- définition d'une variable 'actes' contenant les noeuds ngap du document actes.xml -->
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    
    
    <!-- définition d'une variable qui contient le noeud 'patient' en question -->
    <xsl:variable name="destinedPatient" select="//cab:patient[cab:nom=$destinedName]"/>
    
    <!-- Ici création du contenu du doc xml -->
    <xsl:template match="/">
        
        <!-- ici, racine 'patient' du document -->
        <xsl:element name="patient">
            
        <!-- rajout du nameSpace du cabinetInfirmier en tant qu'attribut de la racine patient
            <xsl:attribute name="xmlns:cabinet">
                <xsl:value-of select="http://www.ujf-grenoble.fr/l3miage/medical"/>
            </xsl:attribute>
        -->
        
          <!-- ici, état civil du patient -->
            
            <!-- son nom -->
            <xsl:element name="nom">
                <xsl:value-of select="$destinedPatient/cab:nom/text()"/>
            </xsl:element>
            
            <!-- son prénom -->
            <xsl:element name="prénom">
                <xsl:value-of select="$destinedPatient/cab:prénom/text()"/>
            </xsl:element>
            
            <!-- son sexe -->
            <xsl:element name="sexe">
                <xsl:value-of select="$destinedPatient/cab:sexe/text()"/>
            </xsl:element>
            
            <!-- sa date de naissance -->
            <xsl:element name="naissance">
                <xsl:value-of select="$destinedPatient/cab:naissance/text()"/>
            </xsl:element>
            
            <!-- son numéro de sécurité sociale -->
            <xsl:element name="numéroSS">
                <xsl:value-of select="$destinedPatient/cab:numéro/text()"/>
            </xsl:element>
            
            <!-- son adresse postale -->
            <xsl:element name="adresse">
                
                <!-- numéro d'étage s'il éxiste -->
                <xsl:if test="string-length($destinedPatient/cab:adresse/cab:étage) > 0">
                    <xsl:element name="étage">
                        <xsl:value-of select="$destinedPatient/cab:adresse/cab:étage/text()"/>
                    </xsl:element>
                </xsl:if>
                
                <!-- numéro de rue s'il éxiste -->
                <xsl:if test="string-length($destinedPatient/cab:adresse/cab:numéro) > 0">
                    <xsl:element name="numéro">
                        <xsl:value-of select="$destinedPatient/cab:adresse/cab:numéro/text()"/>
                    </xsl:element>
                </xsl:if>
                
                <!-- nom de la rue -->
                <xsl:element name="rue">
                    <xsl:value-of select="$destinedPatient/cab:adresse/cab:rue/text()"/>
                </xsl:element>
                
                <!-- nom de la ville -->
                <xsl:element name="ville">
                    <xsl:value-of select="$destinedPatient/cab:adresse/cab:ville/text()"/>
                </xsl:element>
                
                <!-- numéro de code postal -->
                <xsl:element name="codePostal">
                    <xsl:value-of select="$destinedPatient/cab:adresse/cab:codePostal/text()"/>
                </xsl:element>
                
            </xsl:element>
            
            <!-- ses visites avec les infos suivantes pour chaque visite :
                - date de visite
                - nom et prénom de l'intervenant
                - libellé de l'acte médical
                Vue qu'un patient peut avoir plusieurs visites, on appliquera un template 
                qui traitera automatiquement chaque noeud 'visite'
            -->
            
            <xsl:apply-templates select="$destinedPatient/cab:visite">
                <xsl:sort select="@date" order="descending"/>
            </xsl:apply-templates>
            
        </xsl:element>
    </xsl:template>
    
    <!-- ici, traitement du noeud visite. On affiche les infos suivantes :
        - date de visite
        - nom et prénom de l'intervenant
        - libellé de l'acte médical
    -->
    <xsl:template match="cab:visite">
        
        <!-- définition d'une variable idIntervenantCourant et qui a pour valeur l'id de l'intervenant de la visite courante -->
        <xsl:variable name="idIntervenantCourant" select="@intervenant"/>
        
        <!-- on crée un élément 'visite' -->
        <xsl:element name="visite">
            
            <!-- On ajoute l'attribut 'date' à l'élément 'visite' et qui contient
                comme valeur la date de la visite en question
            -->
            <xsl:attribute name="date">
                <xsl:value-of select="@date"/>
            </xsl:attribute>
            
            <!-- On ajoute l'element 'intervenant' à l'élément 'visite' et qui contient
                les elements 'nom' et 'prénom' de l'intervenant
            -->
            <xsl:element name="intervenant">
                
                <!-- nom de l'intervenant -->
                <xsl:element name="nom">
                    <xsl:value-of select="//cab:infirmier[@id=$idIntervenantCourant]/cab:nom"/>
                </xsl:element>
                
                <!-- prénom de l'intervenant -->
                <xsl:element name="prénom">
                    <xsl:value-of select="//cab:infirmier[@id=$idIntervenantCourant]/cab:prénom"/>
                </xsl:element>
                
            </xsl:element>
            
            <!-- On ajoute les actes pour cette visite 
                Vue qu'un patient peut avoir plusieurs actes, on appliquera un template 
                qui traitera automatiquement chaque noeud 'acte'
            -->
            <xsl:apply-templates select="cab:acte"/>
            
        </xsl:element>
            
    </xsl:template>
    
    <!-- ici, traitement du noeud acte. On affiche le libellé exacte de l'acte médical -->
    <xsl:template match="cab:acte">
        
        <!-- définition d'une variable idActeCourant et qui a pour valeur l'id de l'acte courant -->
        <xsl:variable name="idActeCourant" select="@id"/>
        
        <!-- on crée un élément 'acte' qui a pour valeur le libellé de l'acte courant -->
        <xsl:element name="acte">
            <!-- on selectionne depuis le doc actes.xml le libellé de l'acte dont l'id correspond à l'id de l'acte courant -->
            <xsl:value-of select="$actes//act:acte[@id=$idActeCourant]/text()"/>
        </xsl:element>
        
    </xsl:template>

</xsl:stylesheet>
