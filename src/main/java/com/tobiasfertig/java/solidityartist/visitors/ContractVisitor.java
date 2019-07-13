package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
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

	}

	@Override public void visit( ConstructorElement element )
	{

	}

	@Override public void visit( ContractElement element )
	{

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
		appendCollectionWithDelimiter( element.getValues( ), ",\n" );
		closeCurlyBraces( );
	}

	@Override public void visit( EventElement element )
	{

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

	@Override public void visit( MemoryParameterElement element )
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

	@Override public void visit( ModifierElement element )
	{

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

	}

	@Override public void visit( StorageParameterElement element )
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

	@Override public void visit( StructElement element )
	{
		indent( );
		sb.append( Keywords.STRUCT );
		space( );
		sb.append( element.getName( ) );
		space( );
		openCurlyBraces( );
		appendCollectionOfSolidityElementsWithDelimiter( element.getStructMembers( ), ";\n" );
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
		newline( );
	}

	private void appendCollectionWithDelimiter( Iterable<? extends Object> set, String delimiter )
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

	private void appendCollectionOfSolidityElementsWithDelimiter(
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
}
