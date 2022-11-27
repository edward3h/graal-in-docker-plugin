package org.ethelred.graal.in.docker.plugin;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public interface MyNativeExtension
{
    Property<String> getDockerImageName();
    Property<String> getMainClassName();

    Property<String> getAppName();

    Property<String> getUser();
    MapProperty<String, String> getGenerateNativeConfigEnvironment();

}
