package com.tobiasfertig.java.solidityartist.visitors;

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
import com.tobiasfertig.java.solidityartist.utils.Keyword;

public class ContractVisitor extends VisitorImpl
{
	public ContractVisitor( )
	{
	}

	@Override public String export( )
	{
		return sb.toString( );
	}

	@Override public void visit( CodeElement element )
	{
		appendCollection( element.getLines( ), "\n" );
	}

	@Override public void visit( ConstructorElement element )
	{
		sb.append( Keyword.CONSTRUCTOR );
		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );

		for ( String inheritanceModifier : element.getInheritanceModifiers( ) )
		{
			space( );
			sb.append( inheritanceModifier );
		}

		space( );
		sb.append( element.getVisibility( ) );

		for ( Keyword.Modifier modifier : element.getModifiers( ) )
		{
			space( );
			sb.append( modifier );
		}

		for ( String customModifier : element.getCustomModifiers( ) )
		{
			space( );
			sb.append( customModifier );
		}

		space( );
		openCurlyBraces( );

		if(element.getCode() != null)
		{
			element.getCode( ).accept( this );
		}

		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( ContractElement element )
	{
		sb.append( Keyword.CONTRACT );
		space( );
		sb.append( element.getName( ) );

		if ( !element.getInheritedContracts( ).isEmpty( ) )
		{
			space( );
			sb.append( Keyword.IS );
			space( );
			appendCollectionInline( element.getInheritedContracts( ), ", " );
		}

		space( );
		openCurlyBraces( );

		if ( !element.getUsingForDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getUsingForDeclarations( ), "\n" );
			newline( );
			newline( );
		}

		if ( !element.getEnumDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getEnumDeclarations( ), "\n\n" );
			newline( );
			newline( );
		}

		if ( !element.getStructDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getStructDeclarations( ), "\n\n" );
			newline( );
			newline( );
		}

		if ( !element.getStateVariables( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getStateVariables( ), "\n" );
			newline( );
			newline( );
		}

		if ( !element.getEventDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getEventDeclarations( ), "\n" );
			newline( );
			newline( );
		}

		if ( !element.getModifierDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getModifierDeclarations( ), "\n" );
			newline( );
			newline( );
		}

		if ( element.getConstructor( ) != null )
		{
			indent( );
			element.getConstructor( ).accept( this );
			newline( );
			newline( );
		}

		if ( element.getFallbackFunction( ) != null )
		{
			indent( );
			element.getFallbackFunction( ).accept( this );
			newline( );
			newline( );
		}

		if ( !element.getExternalFunctions( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getExternalFunctions( ), "\n\n\n" );
			newline( );
			newline( );
		}

		if ( !element.getPublicFunctions( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getPublicFunctions( ), "\n\n\n" );
			newline( );
			newline( );
		}

		if ( !element.getInternalFunctions( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getInternalFunctions( ), "\n\n\n" );
			newline( );
			newline( );
		}

		if ( !element.getPrivateFunctions( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getPrivateFunctions( ), "\n\n\n" );
			newline( );
			newline( );
		}

		closeCurlyBraces( );
	}

	@Override public void visit( DataLocationParameterElement element )
	{
		element.getDataType( ).accept( this );
		space( );
		sb.append( element.getDataLocation( ) );

		if ( element.getName( ) != null )
		{
			space( );
			sb.append( element.getName( ) );
		}
	}

	@Override public void visit( DataTypeElement element )
	{
		sb.append( element.getTypeName( ) );
	}

	@Override public void visit( EnumElement element )
	{
		sb.append( Keyword.ENUM );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollection( element.getValues( ), ",\n" );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( EventElement element )
	{
		sb.append( Keyword.EVENT );
		space( );
		sb.append( element.getName( ) );
		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );
		semicolon( );
	}

	@Override public void visit( EventParameterElement element )
	{
		element.getDataType( ).accept( this );
		space( );
		sb.append( element.getAttribute( ) );

		if ( element.getName( ) != null )
		{
			space( );
			sb.append( element.getName( ) );
		}
	}

	@Override public void visit( FileElement element )
	{

	}

	@Override public void visit( FunctionElement element )
	{
		sb.append( Keyword.FUNCTION );

		if ( !element.isFallback( ) )
		{
			space( );
			sb.append( element.getName( ) );
		}

		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );
		space( );
		sb.append( element.getVisibility( ) );

		for ( Keyword.Modifier modifier : element.getModifiers( ) )
		{
			space( );
			sb.append( modifier );
		}

		for ( String customModifier : element.getCustomModifiers( ) )
		{
			space( );
			sb.append( customModifier );
		}

		if ( !element.getReturnParameters( ).isEmpty( ) )
		{
			space( );
			sb.append( Keyword.RETURNS );
			openBraces( );
			appendCollectionOfSolidityElementsInline( element.getReturnParameters( ), ", " );
			closeBraces( );
		}

		if ( element.isAbstract( ) )
		{
			semicolon( );
		}
		else
		{
			space( );
			openCurlyBraces( );
			element.getCode( ).accept( this );
			newline( );
			closeCurlyBraces( );
		}
	}

	@Override public void visit( FunctionTypeElement element )
	{
		sb.append( element.getTypeName( ) );
		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );
		space( );
		sb.append( element.getVisibility( ) );

		for ( Keyword.Modifier modifier : element.getModifiers( ) )
		{
			space( );
			sb.append( modifier );
		}

		if ( !element.getReturnParameters( ).isEmpty( ) )
		{
			space( );
			sb.append( Keyword.RETURNS );
			openBraces( );
			appendCollectionOfSolidityElementsInline( element.getReturnParameters( ), ", " );
			closeBraces( );
		}
	}

	@Override public void visit( ImportElement element )
	{

	}

	@Override public void visit( InterfaceElement element )
	{

	}

	@Override public void visit( LibraryElement element )
	{

	}

	@Override public void visit( MappingElement element )
	{
		sb.append( element.getTypeName( ) );
		openBraces( );
		element.getKeyType( ).accept( this );
		space( );
		sb.append( "=>" );
		space( );
		element.getValueType( ).accept( this );
		closeBraces( );
	}

	@Override public void visit( ModifierElement element )
	{
		sb.append( Keyword.MODIFIER );
		space( );
		sb.append( element.getName( ) );
		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );
		space( );
		openCurlyBraces( );
		element.getCode( ).accept( this );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( ParameterElement element )
	{
		element.getDataType( ).accept( this );

		if ( element.getName( ) != null )
		{
			space( );
			sb.append( element.getName( ) );
		}
	}

	@Override public void visit( PragmaElement element )
	{

	}

	@Override public void visit( StateVariableElement element )
	{
		element.getDataType( ).accept( this );
		space( );
		sb.append( element.getVisibility( ) );
		space( );

		if ( element.isConstant( ) )
		{
			sb.append( Keyword.CONSTANT );
			space( );
		}

		sb.append( element.getName( ) );

		if ( !element.getInitialization( ).isEmpty( ) )
		{
			space( );
			sb.append( "=" );
			space( );
			sb.append( element.getInitialization( ) );
		}

		semicolon( );
	}

	@Override public void visit( StructElement element )
	{
		sb.append( Keyword.STRUCT );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollectionOfSolidityElements( element.getStructMembers( ), ";\n" );
		semicolon( );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( UsingForElement element )
	{
		sb.append( Keyword.USING );
		space( );
		element.getExtension( ).accept( this );
		space( );
		sb.append( Keyword.FOR );
		space( );
		element.getSource( ).accept( this );
		semicolon( );
	}
}
