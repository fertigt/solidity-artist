package com.tobiasfertig.java.solidityartist.elements.comments;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.utils.NatSpecTag;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class NatSpecElement implements SolidityElement
{
	private final List<String> lines;
	private final boolean isMultiLineComment;

	private NatSpecElement( Builder builder )
	{
		this.lines = builder.lines;
		this.isMultiLineComment = builder.isMultiLineComment;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public List<String> getLines( )
	{
		return lines;
	}

	public boolean isMultiLineComment( )
	{
		return isMultiLineComment;
	}

	public static Builder builder( )
	{
		return new Builder( );
	}

	public static final class Builder
	{
		private final List<String> lines = new ArrayList<>( );
		private boolean isMultiLineComment;

		private Builder( )
		{

		}

		public Builder addLine( String line )
		{
			this.lines.add( line );
			return this;
		}

		public Builder addLines( Iterable<String> lines )
		{
			for ( String line : lines )
			{
				this.lines.add( line );
			}

			return this;
		}

		public Builder isMultiLineComment( )
		{
			this.isMultiLineComment = true;
			return this;
		}

		public Builder addTagAtTitle( String line )
		{
			this.lines.add( NatSpecTag.TITLE + " " + line );
			return this;
		}

		public Builder addTagAtAuthor( String line )
		{
			this.lines.add( NatSpecTag.AUTHOR + " " + line );
			return this;
		}

		public Builder addTagAtNotice( String line )
		{
			this.lines.add( NatSpecTag.NOTICE + " " + line );
			return this;
		}

		public Builder addTagAtDev( String line )
		{
			this.lines.add( NatSpecTag.DEV + " " + line );
			return this;
		}

		public Builder addTagAtParam( String line )
		{
			this.lines.add( NatSpecTag.PARAM + " " + line );
			return this;
		}

		public Builder addTagAtReturn( String line )
		{
			this.lines.add( NatSpecTag.RETURN + " " + line );
			return this;
		}

		public NatSpecElement build( )
		{
			return new NatSpecElement( this );
		}
	}
}
