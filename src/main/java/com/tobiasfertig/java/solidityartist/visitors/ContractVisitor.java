package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.ContractElement;
import com.tobiasfertig.java.solidityartist.elements.files.ImportElement;
import com.tobiasfertig.java.solidityartist.elements.files.InterfaceElement;
import com.tobiasfertig.java.solidityartist.elements.files.LibraryElement;
import com.tobiasfertig.java.solidityartist.elements.functions.*;
import com.tobiasfertig.java.solidityartist.elements.parameters.DataLocationParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.EventParameterElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import com.tobiasfertig.java.solidityartist.utils.Keywords;

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
		appendCollection( element.getLines( ), "\n" );
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
		sb.append( Keywords.ENUM );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollection( element.getValues( ), ",\n" );
		closeCurlyBraces( );
	}

	@Override public void visit( EventElement element )
	{
		indent( );
		sb.append( Keywords.EVENT );
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

	@Override public void visit( FunctionElement element )
	{

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

	@Override public void visit( ModifierElement element )
	{
		indent( );
		sb.append( Keywords.MODIFIER );
		space( );
		sb.append( element.getName( ) );
		openBraces( );
		appendCollectionOfSolidityElementsInline( element.getParameters( ), ", " );
		closeBraces( );
		space( );
		openCurlyBraces( );
		element.getCode( ).accept( this );
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

	@Override public void visit( StateVariableElement element )
	{
		indent( );
		element.getDataType( ).accept( this );
		space( );
		sb.append( element.getVisibility( ) );
		space( );

		if ( element.isConstant( ) )
		{
			sb.append( Keywords.CONSTANT );
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
		sb.append( Keywords.STRUCT );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollectionOfSolidityElements( element.getStructMembers( ), ";\n" );
		semicolon( );
		closeCurlyBraces( );
	}

	@Override public void visit( UsingForElement element )
	{
		indent( );
		sb.append( Keywords.USING );
		space( );
		element.getExtension( ).accept( this );
		space( );
		sb.append( Keywords.FOR );
		space( );
		element.getSource( ).accept( this );
		semicolon( );
	}

	private void appendCollection( Iterable<? extends Object> set, String delimiter )
	{
		Iterator<? extends Object> iterator = set.iterator( );
		if ( iterator.hasNext( ) )
		{
			Object nextValue = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				indent( );
				sb.append( nextValue.toString( ) );
				sb.append( delimiter );

				nextValue = iterator.next( );
			}

			indent( );
			sb.append( nextValue );
		}
	}

	private void appendCollectionOfSolidityElements(
		Iterable<? extends SolidityElement> elements,
		String delimiter
	)
	{
		Iterator<? extends SolidityElement> iterator = elements.iterator( );
		if ( iterator.hasNext( ) )
		{
			SolidityElement nextElement = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				indent( );
				nextElement.accept( this );
				sb.append( delimiter );

				nextElement = iterator.next( );
			}

			indent( );
			nextElement.accept( this );
		}
	}

	private void appendCollectionOfSolidityElementsInline(
		Iterable<? extends SolidityElement> elements,
		String delimiter
	)
	{
		Iterator<? extends SolidityElement> iterator = elements.iterator( );
		if ( iterator.hasNext( ) )
		{
			SolidityElement nextElement = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				nextElement.accept( this );
				sb.append( delimiter );

				nextElement = iterator.next( );
			}

			indent( );
			nextElement.accept( this );
		}
	}
}
