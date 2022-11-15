package org.ethelred.graal.in.docker.plugin;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public class MyNativeExtension
{
    @SuppressWarnings("UnstableApiUsage")
    @Inject
    public MyNativeExtension(ObjectFactory objects)
    {
        dockerImageName = objects.property(String.class);
        dockerImageName.convention("ghcr.io/edward3h/graal-fcgi-fake-dh-builder:v0.8");
        mainClassName = objects.property(String.class);
        appName = objects.property(String.class);
        generateNativeConfigEnvironment = objects.mapProperty(String.class, String.class);
        user = objects.property(String.class);
    }

    public Property<String> getDockerImageName()
    {
        return dockerImageName;
    }

    public void setDockerImageName(String dockerImageName)
    {
        this.dockerImageName.set(dockerImageName);
    }

    public Property<String> getMainClassName()
    {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName)
    {
        this.mainClassName.set(mainClassName);
    }

    public Property<String> getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName.set(appName);
    }

    public Property<String> getUser() {
        return user;
    }

    private final Property<String> dockerImageName;
    private final Property<String> mainClassName;
    private final Property<String> appName;

    public MapProperty<String, String> getGenerateNativeConfigEnvironment()
    {
        return generateNativeConfigEnvironment;
    }

    private final MapProperty<String, String> generateNativeConfigEnvironment;

    private final Property<String> user;
}
