package com.tobiasfertig.java.solidityartist.elements;

import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public interface SolidityElement
{
	void accept( Visitor visitor );
}
