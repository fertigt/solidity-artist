package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.FunctionTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.MappingElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.*;
import com.tobiasfertig.java.solidityartist.elements.functions.*;
import com.tobiasfertig.java.solidityartist.elements.parameters.DataLocationParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.EventParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
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

	void visit( DataLocationParameterElement element );

	void visit( DataTypeElement element );

	void visit( EnumElement element );

	void visit( EventElement element );

	void visit( EventParameterElement element );

	void visit( FileElement element );

	void visit( FunctionElement element );

	void visit( FunctionTypeElement element );

	void visit( ImportElement element );

	void visit( InterfaceElement element );

	void visit( LibraryElement element );

	void visit( MappingElement element );

	void visit( ModifierElement element );

	void visit( NatSpecElement element );

	void visit( ParameterElement element );

	void visit( PragmaElement element );

	void visit( StateVariableElement element );

	void visit( StructElement element );

	void visit( UsingForElement element );
}
