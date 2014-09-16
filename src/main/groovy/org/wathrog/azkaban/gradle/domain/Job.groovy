package org.wathrog.azkaban.gradle.domain

class Job {
    final String name
    JobType type
    def String dependencies

    public String getType() {
        return type.name;
    }

    public void setType(String type) {
        this.type = type.toUpperCase() as JobType;
    }

    Job(String name) {
        this.name = name
    }

    // Job type specific stuff
    def String command
    def String flow
    def String jobClass
    def String mainArgs = '${param.inData} ${param.outData}'
    def String classpath = './lib/*,${hadoop.home}/lib/*'

    public String getJobTypeSpecificProperties() {
        def str = new StringBuilder()
        switch (type) {
            case JobType.COMMAND:
                str << "command=$command"
                break
            case JobType.FLOW:
                str << "flow.name=$flow"
                break
            case JobType.HADOOP:
                str << "job.class=$jobClass"
                str << System.lineSeparator()
                str << "classpath=$classpath"
                str << System.lineSeparator()
                str << "main.args=$mainArgs"
            case JobType.NOOP:
            default:
                break
        }
    }
}

enum JobType {
    COMMAND('command'),
    HADOOP('hadoopJava'),
    FLOW('flow'),
    NOOP('noop')

    def String name

    public JobType(String name) {
        this.name = name;
    }
}
