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

import java.util.Collection;
import java.util.Iterator;

public class InterfaceVisitor extends VisitorImpl
{
	public InterfaceVisitor( int lineLength )
	{
		super( lineLength );
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

		int startIndex = sb.length( );
		appendFunctionSignatureInline( element );
		int endIndex = sb.length( );

		if ( endIndex - startIndex > this.lineLength )
		{
			sb.delete( startIndex, endIndex );
			appendFunctionSignatureUntilParametersInline( element );
			endIndex = sb.length( );

			if ( endIndex - startIndex > this.lineLength )
			{
				sb.delete( startIndex, endIndex );
				appendFunctionSignatureUntilParametersLineWrapped( element );
			}

			appendFunctionSignatureAfterParametersLineWrapped( element );
		}
	}

	private void appendFunctionSignatureInline( FunctionElement element )
	{
		indent( );

		sb.append( Keyword.FUNCTION );
		appendNameIfIsNotFallback( element );
		appendParametersInline( element.getParameters( ) );

		space( );
		sb.append( element.getVisibility( ) );
		appendObjectsSpaceSeparatedInline( element.getModifiers( ) );
		appendObjectsSpaceSeparatedInline( element.getCustomModifiers( ) );

		if ( !element.getReturnParameters( ).isEmpty( ) )
		{
			space( );
			sb.append( Keyword.RETURNS );
			appendParametersInline( element.getReturnParameters( ) );
		}

		semicolon( );
	}

	private void appendObjectsSpaceSeparatedInline( Collection<? extends Object> elements )
	{
		for ( Object element : elements )
		{
			space( );
			sb.append( element );
		}
	}

	private void appendFunctionSignatureUntilParametersInline( FunctionElement element )
	{
		indent( );

		sb.append( Keyword.FUNCTION );
		appendNameIfIsNotFallback( element );
		appendParametersInline( element.getParameters( ) );
	}

	private void appendParametersInline(Iterable<ParameterElement> elements )
	{
		openBraces( );
		appendCollectionOfSolidityElements( elements, ", " );
		closeBraces( );
	}

	private void appendFunctionSignatureUntilParametersLineWrapped( FunctionElement element )
	{
		indent( );

		sb.append( Keyword.FUNCTION );
		appendNameIfIsNotFallback( element );
		openBraces( );
		newline( );

		increaseIndentation( );
		appendCollectionOfSolidityElementsIndented( element.getParameters( ), ",\n" );
		decreaseIndentation( );
		newline( );

		indent( );
		closeBraces( );
	}

	private void appendNameIfIsNotFallback( FunctionElement element )
	{
		if ( !element.isFallback( ) )
		{
			space( );
			sb.append( element.getName( ) );
		}
	}

	private void appendFunctionSignatureAfterParametersLineWrapped( FunctionElement element )
	{
		increaseIndentation( );

		newline( );
		indent( );
		sb.append( element.getVisibility( ) );

		appendObjectsWrappedAndIndented( element.getModifiers( ) );
		appendObjectsWrappedAndIndented( element.getCustomModifiers( ) );
		appendReturnParametersLineWrapped( element.getReturnParameters( ) );

		decreaseIndentation( );

		semicolon( );
	}

	private void appendObjectsWrappedAndIndented( Collection<? extends Object> elements )
	{
		for ( Object element : elements )
		{
			newline( );
			indent( );
			sb.append( element );
		}
	}

	private void appendReturnParametersLineWrapped( Collection<ParameterElement> returnParameters )
	{
		if ( !returnParameters.isEmpty( ) )
		{
			newline( );

			indent( );
			sb.append( Keyword.RETURNS );
			openBraces( );

			newline( );
			increaseIndentation( );
			appendCollectionOfSolidityElementsIndented( returnParameters, ",\n" );
			decreaseIndentation( );

			newline( );
			indent( );
			closeBraces( );
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
		checkAndAppendComment( element.getComment( ) );
		sb.append( Keyword.INTERFACE );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );

		boolean isNotFirstElement = false;
		isNotFirstElement = checkAndAppend( element.getUsingForDeclarations( ), isNotFirstElement, "\n" );
		isNotFirstElement = checkAndAppend( element.getEnumDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getStructDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getEventDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getModifierDeclarations( ), isNotFirstElement, "\n\n" );
		isNotFirstElement = checkAndAppend( element.getFallbackFunction( ), isNotFirstElement );
		checkAndAppend( element.getExternalFunctions( ), isNotFirstElement, "\n\n" );

		newline( );
		closeCurlyBraces( );
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
		checkAndAppendComment( element.getComment( ) );
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
