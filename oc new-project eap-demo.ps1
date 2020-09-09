<# 
    Deploys the following to OpenShift

        1. Project
        2. Applictaion
        3. Add Templates
 #>

#region Login into Hub.Docker.com, Quay.io, and Registry.RedHat.io

docker login -u="plucas-redhat-com+parris_redhat_com" quay.io
docker login --username parris4redhat #hub.docker.io
docker login registry.access.redhat.com
docker login registry.redhat.io 
#endregion

#region OpenShift Variables

$OC_CURRENT_CLUSTER_USER = $(oc whoami)
$OC_CURRENT_CLUSTER_TOKEN = $(oc whoami -t)
$OC_CURRENT_CLUSTER_CONTEXT = $(oc config current-context)
#endregion


#region Local Environment Variables

$Env:PATH = "C:\Users\Developer\.crc\bin\oc;$Env:PATH"
$CRC_PATH = "D:\Developer\CRC\"
$EAP_HOME   = "D:\volume\sandbox\jboss-eap-7.3\"
$EAP7_HOME  = "D:\volume\sandbox\jboss-eap-7.3\bin\"
$JBOSS_HOME  = "D:\volume\sandbox\jboss-eap-7.3\"
#endregion

#region OpenShift Deployment Variables
$OC_SECRET_PATH = "$($CRC_PATH).\secrets\"
$OC_EAP_PROJECT = "eap-demo-03"
$JBOSS_EAP_IMAGE_DIRECTORY = "eap73-openjdk11"
$JBOSS_EAP_IMAGE_URL = "https://raw.githubusercontent.com/jboss-container-images/jboss-eap-7-openshift-image/$($JBOSS_EAP_IMAGE_DIRECTORY)/templates/"
#endregion

oc new-project $OC_EAP_PROJECT

#region Adding registry.redhat.io secret

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
#endregion

#region import current OS Image from registry.redhat.io

# JBoss EAP 7
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/jboss-eap-7/eap73-openjdk11-openshift-rhel8 --confirm
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/jboss-eap-7/eap73-openjdk11-runtime-openshift-rhel8 --confirm
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/jboss-eap-7-tech-preview/eap73-openjdk11-openshift-rhel8  --confirm
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/jboss-eap-7-tech-preview/eap-cd-runtime-openshift-rhel8 --confirm

# PostgreSQL 12
oc -n $OC_EAP_PROJECT import-image registry.redhat.io/rhel8/postgresql-12 --confirm
#endregion 

<# An example Red Hat JBoss EAP 7 application. For more information about using this template, 
see https://github.com/jboss-container-images/jboss-eap-7-openshift-image/blob/eap72/README.adoc #>

<# commented out to prevent running
# Install basic EAP 7.x, openJDK 11 S2I on to OpenShift

oc -n $OC_EAP_PROJECT new-app eap73-openjdk11-basic-s2i -p APPLICATION_NAME=eap73-basic-app

 #>

#region JBOSS EAP Quickstart Helloworld HTML5

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
#endregion
 
#region JBOSS EAP Quickstart Hibernate 

 <# commented out to prevent running
# Install JBOSS EAP Quickstart Hibernate with specific tag
# Powershell continue next line is "`" and replaced bash "\"

oc new-app --template=eap73-openjdk11-basic-s2i `
 -p IMAGE_STREAM_NAMESPACE=$OC_EAP_PROJECT `
 -p SOURCE_REPOSITORY_URL=https://github.com/jboss-developer/jboss-eap-quickstarts `
 -p SOURCE_REPOSITORY_REF=7.3.x-openshift `
 -p GALLEON_PROVISION_LAYERS=jaxrs-server `
 -p CONTEXT_DIR=helloworld-html5
 
 #>

#endregion