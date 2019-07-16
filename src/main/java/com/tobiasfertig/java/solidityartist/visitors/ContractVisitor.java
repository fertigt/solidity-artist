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
import com.tobiasfertig.java.solidityartist.utils.Keyword;

import java.util.Iterator;

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
		appendCollectionIndented( element.getLines( ), "\n" );
	}

	@Override public void visit( ConstructorElement element )
	{
		if ( element.getComment( ) != null )
		{
			element.getComment( ).accept( this );
			newline( );
		}

		indent( );
		sb.append( Keyword.CONSTRUCTOR );
		openBraces( );
		appendCollectionOfSolidityElements( element.getParameters( ), ", " );
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
		if ( element.getComment( ) != null )
		{
			element.getComment( ).accept( this );
			newline( );
		}

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

		boolean isNotFirstElement = false;
		if ( !element.getUsingForDeclarations( ).isEmpty( ) )
		{
			appendCollectionOfSolidityElements( element.getUsingForDeclarations( ), "\n" );
			isNotFirstElement = true;
		}

		if ( !element.getEnumDeclarations( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getEnumDeclarations( ), "\n\n" );
			isNotFirstElement = true;
		}

		if ( !element.getStructDeclarations( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getStructDeclarations( ), "\n\n" );
			isNotFirstElement = true;
		}

		if ( !element.getStateVariables( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getStateVariables( ), "\n" );
			isNotFirstElement = true;
		}

		if ( !element.getEventDeclarations( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getEventDeclarations( ), "\n" );
			isNotFirstElement = true;
		}

		if ( !element.getModifierDeclarations( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}
			appendCollectionOfSolidityElements( element.getModifierDeclarations( ), "\n" );
			isNotFirstElement = true;
		}

		if ( element.getConstructor( ) != null )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			element.getConstructor( ).accept( this );
			isNotFirstElement = true;
		}

		if ( element.getFallbackFunction( ) != null )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			element.getFallbackFunction( ).accept( this );
			isNotFirstElement = true;
		}

		if ( !element.getExternalFunctions( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getExternalFunctions( ), "\n\n" );
			isNotFirstElement = true;
		}

		if ( !element.getPublicFunctions( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getPublicFunctions( ), "\n\n" );
			isNotFirstElement = true;
		}

		if ( !element.getInternalFunctions( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getInternalFunctions( ), "\n\n" );
			isNotFirstElement = true;
		}

		if ( !element.getPrivateFunctions( ).isEmpty( ) )
		{
			if ( isNotFirstElement )
			{
				newline( );
				newline( );
			}

			appendCollectionOfSolidityElements( element.getPrivateFunctions( ), "\n\n" );
		}

		newline( );
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
		if ( element.getComment( ) != null )
		{
			element.getComment( ).accept( this );
			newline( );
		}

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
