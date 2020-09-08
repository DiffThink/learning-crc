
#region New Project

# Login into Hub.Docker.com, Quay.io, and Registry.RedHat.io


# Login into OpenShift


$OC-EAP-Project = 'eap-demo-02'

$JBOSS-EAP-Image-Directory = 'eap73-openjdk11'
$JBOSS-EAP-Image-URL = 'https://raw.githubusercontent.com/jboss-container-images/jboss-eap-7-openshift-image/$JBOSS-EAP-Image-Directory/templates/'


oc new-project $OC-EAP-Project

# Adding registry.redhat.io secret
oc create -f .\12666202_idm-odo-sa-secret --namespace=$OC-EAP-Project
oc create -f .\12666202_parris-redhat-com-secret.yaml --namespace=$OC-EAP-Project
#endregion

#region Add missing templates jboss-eap-7-openshift-image/eap73-openjdk11
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-image-stream.json
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-amq-persistent-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-amq-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-basic-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-https-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-sso-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-third-party-db-s2i.json 
oc replace --force -n $OC-EAP-Project -f $JBOSS-EAP-Image-Directoryeap73-openjdk11-tx-recovery-s2i.json

# import current OS Image from registry.redhat.io
oc -n $OC-EAP-Project import-image registry.redhat.io/jboss-eap73-openshift-rhel8:TP --confirm

<# An example Red Hat JBoss EAP 7 application. For more information about using this template, 
see https://github.com/jboss-container-images/jboss-eap-7-openshift-image/blob/eap72/README.adoc #>


<# commented out to prevent running
# Install basic EAP 7.x, openJDK 11 S2I on to OpenShift

oc -n $OC-EAP-Project new-app eap73-openjdk11-basic-s2i -p APPLICATION_NAME=eap73-basic-app
 #>
 


<# commented out to prevent running
# Install JBOSS EAP Quickstar Hello World HTML5 with specific tag
# Powershell continue next line is "`" and replaced bash "\"

oc new-app --template=eap73-openjdk11-basic-s2i `
 -p IMAGE_STREAM_NAMESPACE=$OC-EAP-Project `
 -p SOURCE_REPOSITORY_URL=https://github.com/jboss-developer/jboss-eap-quickstarts `
 -p SOURCE_REPOSITORY_REF=7.3.x-openshift `
 -p GALLEON_PROVISION_LAYERS=jaxrs-server `
 -p CONTEXT_DIR=helloworld-html5
 #>