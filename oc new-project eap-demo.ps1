
#region New Project

# Login into Hub.Docker.com, Quay.io, and Registry.RedHat.io


# Login into OpenShift

#Local Environment Variables
$Env:PATH = "C:\Users\Developer\.crc\bin\oc;$Env:PATH"
$CRC_PATH = "D:\Developer\CRC\"
$EAP_HOME   = "D:\volume\sandbox\jboss-eap-7.3\"
$EAP7_HOME  = "D:\volume\sandbox\jboss-eap-7.3\bin\"
$JBOSS_HOME  = "D:\volume\sandbox\jboss-eap-7.3\"

#OpenShift Deployment Variables
$OC_SECRET_PATH             = "$($CRC_PATH).\secrets\"
$OC_EAP_PROJECT             = "eap-demo-02"
$JBOSS_EAP_IMAGE_DIRECTORY  = "eap73-openjdk11"
$JBOSS_EAP_IMAGE_URL        = "https://raw.githubusercontent.com/jboss-container-images/jboss-eap-7-openshift-image/$($JBOSS_EAP_IMAGE_DIRECTORY)/templates/"

oc new-project $OC_EAP_PROJECT

# Adding registry.redhat.io secret
oc create -f "$($OC_SECRET_PATH)12666202_idm-odo-sa-secret.yaml" --namespace=$OC_EAP_PROJECT
oc create -f "$($OC_SECRET_PATH)12666202_parris-redhat-com-secret.yaml" --namespace=$OC_EAP_PROJECT
#endregion

#region Add missing templates jboss-eap-7-openshift-image/eap73-openjdk11
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-image-stream.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-amq-persistent-s2i.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-amq-s2i.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-basic-s2i.json" 
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-https-s2i.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-sso-s2i.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-third-party-db-s2i.json"
oc replace --force -n $OC_EAP_PROJECT -f "$($JBOSS_EAP_IMAGE_URL)eap73-openjdk11-tx-recovery-s2i.json"

# import current OS Image from registry.redhat.io
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/jboss-eap73-openshift-rhel8:TP --confirm

<# An example Red Hat JBoss EAP 7 application. For more information about using this template, 
see https://github.com/jboss-container-images/jboss-eap-7-openshift-image/blob/eap72/README.adoc #>


<# commented out to prevent running
# Install basic EAP 7.x, openJDK 11 S2I on to OpenShift

oc -n $OC_EAP_PROJECT new-app eap73-openjdk11-basic-s2i -p APPLICATION_NAME=eap73-basic-app

 #>
 


<# commented out to prevent running
# Install JBOSS EAP Quickstar Hello World HTML5 with specific tag
# Powershell continue next line is "`" and replaced bash "\"

oc new-app --template=eap73-openjdk11-basic-s2i `
 -p IMAGE_STREAM_NAMESPACE=$OC_EAP_PROJECT `
 -p SOURCE_REPOSITORY_URL=https://github.com/jboss-developer/jboss-eap-quickstarts `
 -p SOURCE_REPOSITORY_REF=7.3.x-openshift `
 -p GALLEON_PROVISION_LAYERS=jaxrs-server `
 -p CONTEXT_DIR=helloworld-html5
 
 #>