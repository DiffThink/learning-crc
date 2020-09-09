<# Information
    Deploys the following to OpenShift

        1. Project
        2. Applictaion
        3. Add Templates
 #>
        
#region Login into Hub.Docker.com, Quay.io, and Registry.RedHat.io

# Login with existing credentials 
docker login
docker login quay.io
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
$OC_EAP_GUID = (New-Guid).ToString().Substring(0,8)
#endregion

$OC_EAP_PROJECT = "dev-eap-$($OC_EAP_GUID)"    # Dirty Random Generator

# https://github.com/jboss-container-images/jboss-eap-7-openshift-image/branches
$JBOSS_EAP_IMAGE_DIRECTORY = "7.3.x"  # Tags eap72-openjdk11-ubi8-dev, eap-cd-dev, eap-cd

#region OpenShift Deployment Variables
$OC_SECRET_PATH = "$($CRC_PATH).\secrets\"
$JBOSS_EAP_IMAGE_URL = "https://raw.githubusercontent.com/jboss-container-images/jboss-eap-7-openshift-image/$($JBOSS_EAP_IMAGE_DIRECTORY)/templates/"
#endregion

oc new-project $OC_EAP_PROJECT

#region Registry & SSL/TLS
# Adding registry.redhat.io secret
oc create -f "$($OC_SECRET_PATH)12666202_idm-odo-sa-secret.yaml" -n $OC_EAP_PROJECT
oc create -f "$($OC_SECRET_PATH)12666202_parris-redhat-com-secret.yaml" -n $OC_EAP_PROJECT

# project development certificates SSL/TLS
oc create -n $OC_EAP_PROJECT -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/secrets/eap-app-secret.json
oc create -n $OC_EAP_PROJECT -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/secrets/eap7-app-secret.json
oc create -n $OC_EAP_PROJECT -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/secrets/sso-app-secret.json
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
# Install JBOSS EAP Quickstart with specific tag
# Powershell continue next line is "`" and replaced bash "\"
#>

$OC_PROJECT_APPLICATION_NAME = "test"

$OC_PROJECT_REPOSITORY_URL = "https://github.com/jboss-developer/jboss-eap-quickstarts"
$OC_PROJECT_REPOSITORY_BRANCH = "7.3.x-openshift" 
$OC_PROJECT_REPOSITORY_DIRECTORY = "helloworld"
$OC_PROJECT_REPOSTORY_TEMPLATE = "eap73-openjdk11-basic-s2i"
$OC_PROJECT_REPOSITORY_GALLEON_PROVISION = ""

#region JBOSS EAP Quickstart Helloworld Text

# Install helloworld
$OC_PROJECT_REPOSITORY_DIRECTORY = "helloworld"
oc -n $OC_EAP_PROJECT new-app  `
    --template=$OC_PROJECT_REPOSTORY_TEMPLATE `
    -p IMAGE_STREAM_NAMESPACE=$OC_EAP_PROJECT `
    -p APPLICATION_NAME=$OC_PROJECT_APPLICATION_NAME-$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p SOURCE_REPOSITORY_URL=$OC_PROJECT_REPOSITORY_URL `
    -p SOURCE_REPOSITORY_REF=$OC_PROJECT_REPOSITORY_BRANCH `
    -p CONTEXT_DIR=$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p GALLEON_PROVISION_LAYERS=$OC_PROJECT_REPOSITORY_GALLEON_PROVISION
#endregion

#region JBOSS EAP Quickstart Helloworld HTML5

# Install helloworld-html5
$OC_PROJECT_REPOSITORY_DIRECTORY = "helloworld-html5"
$OC_PROJECT_REPOSITORY_GALLEON_PROVISION = "jaxrs-server"

oc -n $OC_EAP_PROJECT new-app  `
    --template=$OC_PROJECT_REPOSTORY_TEMPLATE `
    -p IMAGE_STREAM_NAMESPACE=$OC_EAP_PROJECT `
    -p APPLICATION_NAME=$OC_PROJECT_APPLICATION_NAME-$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p SOURCE_REPOSITORY_URL=$OC_PROJECT_REPOSITORY_URL `
    -p SOURCE_REPOSITORY_REF=$OC_PROJECT_REPOSITORY_BRANCH `
    -p CONTEXT_DIR=$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p GALLEON_PROVISION_LAYERS=$OC_PROJECT_REPOSITORY_GALLEON_PROVISION
#endregion
 
#region JBOSS EAP Quickstart Hibernate 

<# commented out to prevent running
# Install JBOSS EAP Quickstart Hibernate with specific tag
# Powershell continue next line is "`" and replaced bash "\"
#>

# Install Hibernate
$OC_PROJECT_REPOSITORY_URL = "https://github.com/DiffThink/learning-crc"
$OC_PROJECT_REPOSITORY_BRANCH = "feature/osd-eap-hibernate" 
$OC_PROJECT_REPOSITORY_DIRECTORY = ""
$OC_PROJECT_REPOSTORY_TEMPLATE = "eap73-openjdk11-third-party-db-s2i"
$OC_PROJECT_REPOSITORY_GALLEON_PROVISION = "standalone-full"

oc -n $OC_EAP_PROJECT new-app  `
    --template=$OC_PROJECT_REPOSTORY_TEMPLATE `
    -p IMAGE_STREAM_NAMESPACE=$OC_EAP_PROJECT `
    -p APPLICATION_NAME=$OC_PROJECT_APPLICATION_NAME-$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p SOURCE_REPOSITORY_URL=$OC_PROJECT_REPOSITORY_URL `
    -p SOURCE_REPOSITORY_REF=$OC_PROJECT_REPOSITORY_BRANCH `
    -p CONTEXT_DIR=$OC_PROJECT_REPOSITORY_DIRECTORY `
    -p GALLEON_PROVISION_LAYERS=$OC_PROJECT_REPOSITORY_GALLEON_PROVISION

#endregion