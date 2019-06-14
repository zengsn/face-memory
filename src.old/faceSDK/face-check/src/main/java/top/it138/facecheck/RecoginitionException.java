package top.it138.facecheck;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class RecoginitionException extends Exception implements Iterable<Throwable> {
	private static final long serialVersionUID = 1L;


	private static final AtomicReferenceFieldUpdater<RecoginitionException,RecoginitionException> nextUpdater =
            AtomicReferenceFieldUpdater.newUpdater(RecoginitionException.class,RecoginitionException.class,"next");
	
	 public RecoginitionException() {
	        super();
	    }

	    public RecoginitionException(String message) {
	        super(message);
	    }
	
	
	private volatile RecoginitionException next = null;

	/**
	 * 获得下一错误
	 * @return
	 */
	public RecoginitionException getNextException() {
		return (next);
    }
	
	/**
	 * 设置下一错误
	 * @param ex
	 */
	public void setNextException(RecoginitionException ex) {

		RecoginitionException current = this;
        for(;;) {
        	RecoginitionException next=current.next;
            if (next != null) {
                current = next;
                continue;
            }

            if (nextUpdater.compareAndSet(current,null,ex)) {
                return;
            }
            current=current.next;
        }
    }

	public Iterator<Throwable> iterator() {
		return new Iterator<Throwable>() {
			RecoginitionException firstException = RecoginitionException.this;
			RecoginitionException nextException = firstException.getNextException();
			public Throwable next() {
				Throwable throwable = null;
				if (firstException != null) {
					throwable = firstException;
					firstException = null;
				} else if (nextException != null) {
					throwable = nextException;
					nextException = nextException.getNextException();
				}
				else
					throw new NoSuchElementException();
				
				return throwable;
			}
			
			public boolean hasNext() {
				 if(firstException != null || nextException != null)
	                   return true;
	               return false;
			}
		};
	}

}
