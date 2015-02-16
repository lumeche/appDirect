package com.appdirect.model.utils;


import org.springframework.xml.xpath.NodeMapper;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
public abstract class NodeMapperBuilder {

    public static NodeMapper<String> getAtributeMapper(){
        return new NodeMapper<String>() {
            @Override
            public String mapNode(Node node, int nodeNum) throws DOMException {
                return node.getNodeValue();
            }
        };
    }
}
