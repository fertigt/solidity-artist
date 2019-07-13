package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.ContractElement;
import com.tobiasfertig.java.solidityartist.elements.files.ImportElement;
import com.tobiasfertig.java.solidityartist.elements.files.InterfaceElement;
import com.tobiasfertig.java.solidityartist.elements.files.LibraryElement;
import com.tobiasfertig.java.solidityartist.elements.functions.*;
import com.tobiasfertig.java.solidityartist.elements.parameters.MemoryParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.StorageParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;

public interface Visitor
{
	String export( );

	void visit( CodeElement element );

	void visit( ConstructorElement element );

	void visit( ContractElement element );

	void visit( DataTypeElement element );

	void visit( EnumElement element );

	void visit( EventElement element );

	void visit( FunctionElement element );

	void visit( ImportElement element );

	void visit( InterfaceElement element );

	void visit( LibraryElement element );

	void visit( MemoryParameterElement element );

	void visit( ModifierElement element );

	void visit( ParameterElement element );

	void visit( StateVariableElement element );

	void visit( StorageParameterElement element );

	void visit( StructElement element );

	void visit( UsingForElement element );
}
