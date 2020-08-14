FROM alliancecoder/nonos-wildfly-base
COPY ./target/learning-crc.war ${JBOSS_HOME}/standalone/deployments