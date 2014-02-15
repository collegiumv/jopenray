/*
 *  Copyright 2010 jOpenRay, ILM Informatique  
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.jopenray.server.thinclient;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.jopenray.operation.Operation;
import org.jopenray.operation.PadOperation;
import org.jopenray.operation.UnknownACOperation;

public class OperationHistory {

	LinkedList<Operation> l = new LinkedList<Operation>();
	int maxHistory;

	public OperationHistory() {
		maxHistory = 30000;
	}

	public void add(Operation o) {
		synchronized (l) {
			l.add(o);
			if (l.size() > maxHistory) {
				l.removeFirst();
			}
		}

	}

	public void resend(DisplayWriterThread displayWriterThread, int from, int to) {
		System.out.println("Resend: from :" + from + " to " + to);

		DisplayMessage m = new DisplayMessage(displayWriterThread, false);
		int toFind = to - from + 1; // Nombre de packet a trouver

		synchronized (l) {
			ListIterator<Operation> it = l.listIterator();
			int i = from;
			int found = 0;
			while (it.hasNext()) {
				Operation o = (Operation) it.next();
				if (o.getSequence() == i) {
					// System.out.println("OperationHistory.resend() found: seq"
					// + i);
					m.addOperation(o);
					i++;
					found++;
				}
				if (found == toFind) {
					break;
				}
			}
		}
		displayWriterThread.addHighPriorityMessage(m);

		DisplayMessage mstatus = new DisplayMessage(displayWriterThread);
		mstatus.addOperation(new PadOperation());
		mstatus.addOperation(new UnknownACOperation(0, 1, 0, to));
		displayWriterThread.addHighPriorityMessage(mstatus);

		// m.dump();

	}

	public void clear() {
		synchronized (l) {
			l.clear();
		}
	}
}
