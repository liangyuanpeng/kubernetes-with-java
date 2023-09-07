package cn.lank8s.demo.operator.apis;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("lank8s.cn")
@Version("v1alpha1")
//@Version("v1")
@Kind("MyTomcat")
@ShortNames("mt")
public class MyTomcat extends CustomResource<MyTomcatSpec, Void> implements Namespaced {
}
