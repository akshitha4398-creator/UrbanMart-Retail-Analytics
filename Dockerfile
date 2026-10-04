FROM apache/hadoop:3.3.6

ENV HADOOP_HOME=/opt/hadoop
ENV HADOOP_CONF_DIR=/opt/hadoop/etc/hadoop
ENV PATH=/opt/hadoop/bin:/opt/hadoop/sbin:$PATH

WORKDIR /opt/urbanmart

CMD ["/bin/bash"]
