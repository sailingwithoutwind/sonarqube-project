FROM index.alauda.cn/alaudaorg/qaimages:helloworld
LABEL Version="1.1.90790797"
COPY a.sh /
RUN chmod +x /a.sh