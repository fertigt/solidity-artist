package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.FunctionTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.MappingElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.*;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ConstructorElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.DataLocationParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.EventParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;

import java.util.Iterator;

public class LibraryVisitor extends VisitorImpl
{
	public LibraryVisitor( )
	{
	}

	@Override public String export( )
	{
		return sb.toString( );
	}

	@Override public void visit( CodeElement element )
	{
		appendCollectionIndented( element.getLines( ), "\n" );
	}

	@Override public void visit( ConstructorElement element )
	{

	}

	@Override public void visit( ContractElement element )
	{

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
		indent( );
		sb.append( Keyword.ENUM );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollectionIndented( element.getValues( ), ",\n" );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( EventElement element )
	{
		indent( );
		sb.append( Keyword.EVENT );
		space( );
		sb.append( element.getName( ) );
		openBraces( );
		appendCollectionOfSolidityElements( element.getParameters( ), ", " );
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
		checkAndAppendComment( element.getComment( ) );
		indent( );
		sb.append( Keyword.FUNCTION );

		if ( !element.isFallback( ) )
		{
			space( );
			sb.append( element.getName( ) );
		}

		openBraces( );
		appendCollectionOfSolidityElements( element.getParameters( ), ", " );
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
			appendCollectionOfSolidityElements( element.getReturnParameters( ), ", " );
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
		appendCollectionOfSolidityElements( element.getParameters( ), ", " );
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
			appendCollectionOfSolidityElements( element.getReturnParameters( ), ", " );
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
		checkAndAppendComment( element.getComment( ) );
		sb.append( Keyword.LIBRARY );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );

		boolean isNotFirstElement = false;
		isNotFirstElement = checkAndAppend( element.getUsingForDeclarations( ), isNotFirstElement, "\n" );
		isNotFirstElement = checkAndAppend( element.getEnumDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getStructDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getStateVariables( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getEventDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getModifierDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getExternalFunctions( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getPublicFunctions( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getInternalFunctions( ), isNotFirstElement, "\n\n" );
		checkAndAppend( element.getPrivateFunctions( ), isNotFirstElement, "\n\n" );

		newline( );
		closeCurlyBraces( );
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
		if ( element.getComment( ) != null )
		{
			element.getComment( ).accept( this );
			newline( );
		}

		indent( );
		sb.append( Keyword.MODIFIER );
		space( );
		sb.append( element.getName( ) );
		openBraces( );
		appendCollectionOfSolidityElements( element.getParameters( ), ", " );
		closeBraces( );
		space( );
		openCurlyBraces( );
		element.getCode( ).accept( this );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( NatSpecElement element )
	{
		if ( element.isMultiLineComment( ) )
		{
			appendAsMultiLineComment( element );
		}
		else
		{
			appendAsSingleLineComment( element );
		}
	}

	private void appendAsMultiLineComment( NatSpecElement element )
	{
		indent( );
		sb.append( "/**" );
		newline( );

		for ( String line : element.getLines( ) )
		{
			indent( );
			sb.append( " *" );
			space( );
			sb.append( line );
			newline( );
		}

		indent( );
		sb.append( " */" );
	}

	private void appendAsSingleLineComment( NatSpecElement element )
	{
		Iterator<String> iterator = element.getLines( ).iterator( );
		if ( iterator.hasNext( ) )
		{
			String nextLine = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				indent( );
				sb.append( "///" );
				space( );
				sb.append( nextLine );
				newline( );

				nextLine = iterator.next( );
			}

			indent( );
			sb.append( "///" );
			space( );
			sb.append( nextLine );
		}
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
		indent( );
		element.getDataType( ).accept( this );
		space( );
		sb.append( element.getVisibility( ) );
		space( );
		sb.append( Keyword.CONSTANT );
		space( );
		sb.append( element.getName( ) );
		space( );
		sb.append( "=" );
		space( );
		sb.append( element.getInitialization( ) );
		semicolon( );
	}

	@Override public void visit( StructElement element )
	{
		indent( );
		sb.append( Keyword.STRUCT );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollectionOfSolidityElementsIndented( element.getStructMembers( ), ";\n" );
		semicolon( );
		newline( );
		closeCurlyBraces( );
	}

	@Override public void visit( UsingForElement element )
	{
		indent( );
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
