<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : cabinetInfirmier.xsl
    Created on : 7 novembre 2023, 16:51
    Author     : Khalil
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:cab="http://www.ujf-grenoble.fr/l3miage/medical" 
                xmlns:act="http://www.ujf-grenoble.fr/l3miage/actes" version="1.0">
    
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    
    <!-- définition d'une variable 'actes' contenant les noeuds ngap du document actes.xml -->
    <xsl:variable name="actes" select="document('actes.xml', /)/act:ngap"/>
    
    
    <!-- définition d'un Paramètre global 'destinedId' servant à affichier une page dédiée à une infirmière en particulier 
        en se basant sur l'id de l'infirmière
    -->
    <xsl:param name="destinedId" select="001"/>
    
    
    <!-- définition d'une variable 'visitesDuJour' qui contient les noeuds 'visite'
        correspondant aux visites de l'infirmière en question. Pour cela, on choisit les
        noeuds 'visite' telque l'id de l'intervenant correspond à celui de l'infirmière passé en paramètre
    -->
    <xsl:variable name="visitesDuJour" select="//cab:visite[@intervenant=$destinedId]"/>
     
    
    <!-- Ici, création du contenu du document html
        Il contient les infos concernant les visites du jour de l'infirmière notamment 
        les infos sur ses patients (noms, prénoms, adresses et soins à effectuer) et les factures de ses opérations
    -->
    <xsl:template match="/">
        <html>
            <head>
                
                <!-- Liaison feuille HTML-CSS -->
                <link rel="stylesheet" type="text/css" href="../css/cabinet.css"/>
                
                <title>cabinet.xsl</title>
                
                <!-- Code Javascript pour afficher une facture -->
                <script type="text/javascript">
                    <![CDATA[
                            function openFacture(prenom, nom, actes) {
                            var width  = 500;
                            var height = 300;
                            if(window.innerWidth) {
                                var left = (window.innerWidth-width)/2;
                                var top = (window.innerHeight-height)/2;
                            }
                            else {
                                var left = (document.body.clientWidth-width)/2;
                                var top = (document.body.clientHeight-height)/2;
                            }
                            actes = actes.replace(/\n/g, '<br/>');
                            var factureWindow = window.open('','facture','menubar=yes, scrollbars=yes, top='+top+', left='+left+', width='+width+', height='+height+'');
                            factureText = "Facture pour : " + prenom + " " + nom + "<br/>" + "Liste de Soins effectuées : " + "<br/>" + actes;
                            factureWindow.document.write(factureText);
                            }
                        ]]>
                </script>
            </head>
            <body>
                
                <!-- Affichage de la phrase d'accueil 
                    - Recupération du prénom de l'infirmière et calcul du nombre de ses patients
                -->
                Bonjour <xsl:value-of select="cab:cabinet/cab:infirmiers/cab:infirmier[@id=$destinedId]/cab:prénom/text()"/><br/>
                Aujourd'hui, Vous avez <xsl:value-of select="count($visitesDuJour)"/> patient(s)
                
                <!-- A la suite de la phrase d'accueil, on souhaite lister pour chaque patient à visiter (et dans l'ordre de visite), 
                    son nom, 
                    son adresse correctement mise en forme et
                    la liste des soins à effectuer 
                -->
                <ol>
                    <!-- ici on appelle le template sur les noeuds "patient" pour 
                        appliquer le traitement et afficher les infos ci-dessus 
                    -->
                    <xsl:apply-templates select="$visitesDuJour/.."/>
                </ol>
            </body>
        </html>
    </xsl:template>
    
    <!-- Ici, on veut afficher pour ce patient 
        son nom, 
        son adresse correctement mise en forme et
        la liste des soins à effectuer
    -->
    <xsl:template match="cab:patient">
        
        <!-- ici, définition d'une variable idActePatient qui contient les id des actes du patient-->
        <xsl:variable name="idActePatient" select="cab:visite/cab:acte/@id"/>
        
        <li>
            <!-- definition d'une liste qui contient les infos du patient 
                son nom
                son adresse
                sa liste des soins à effectuer
            -->
            <ul>
                
                <!-- Son nom -->
                <li> nom : <xsl:value-of select="cab:nom/text()"/> </li>
                
                <!-- Son adresse -->
                <li> 
                    adresse : 
                    <xsl:value-of select="cab:adresse/cab:numéro/text()"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:rue/text()"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:codePostal/text()"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="cab:adresse/cab:ville/text()"/>
                    <xsl:if test="string-length(cab:adresse/cab:étage) > 0">
                        <xsl:text>, </xsl:text>
                        <xsl:text> étage </xsl:text> <xsl:value-of select="cab:adresse/cab:étage/text()"/>
                    </xsl:if>
                </li>
                
                <!-- sa liste des soins à effectuer -->
                <li> 
                    <xsl:text> Soins à effectuer : </xsl:text> <br/>
                        <!-- ici on appelle un template sur tous les noeuds 'actes' du patient en question 
                            pour afficher sa liste des soins
                        -->
                        <xsl:apply-templates select="$actes//act:acte[@id=$idActePatient]"/>
                </li>          
            </ul>
            
            <!-- definition d'un bouton 'facture' -->
                <xsl:element name="input">
                    <xsl:attribute name="type">button</xsl:attribute>
                    <xsl:attribute name="value">Facture</xsl:attribute>
                    <xsl:attribute name="onclick">
                        openFacture('<xsl:value-of select="cab:prénom/text()"/>',
                                    '<xsl:value-of select="cab:nom/text()"/>',
                                    <!--`<xsl:value-of select="$actes//act:acte[@id=$idActePatient]"/>`-->
                                    `<xsl:apply-templates select="$actes//act:acte[@id=$idActePatient]"/>`
                                    )
                    </xsl:attribute>
                </xsl:element>
        </li>
    </xsl:template>
    
    <!-- ici, pour chaque acte, on affiche le motif -->
    <xsl:template match="act:acte">
        <br/> <xsl:value-of select="text()"/> <br/>
    </xsl:template>
    
</xsl:stylesheet>
