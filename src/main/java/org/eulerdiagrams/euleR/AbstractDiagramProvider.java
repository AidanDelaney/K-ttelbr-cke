package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.AbstractDiagram.*;
import org.eulerdiagrams.vennom.apCircles.AbstractDiagram;
import org.eulerdiagrams.vennom.apCircles.AreaSpecification;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.*;

import org.eulerdiagrams.vennom.graph.Graph;
import org.eulerdiagrams.AbstractDiagram.*;

/**
 * Parses a VennData into an AbstractDiagram and provide access to
 * AreaSpecification and VennDiagram.  In essence, it's a non-generalised
 * adaptor between VennData and AbstractDiagram.
 *
 * We handle all the conversion between the Wilconson syntax and the Rodgers
 * syntax here rather than pushing it into constructors and toString methods
 * of our Contour, Zone and AbstractDiagram objects.  This is because we will
 * want to eventually drop the Rodgers microsyntax.
 */
class AbstractDiagramProvider {
    private org.eulerdiagrams.AbstractDiagram.WeightedAbstractDiagram diagram;

    // The apCircles.AbstractDiagram only likes single letter contour labels.
    // We use this to keep track of the translation between our AbstractDiagram
    // contour labels and the single letter labels.
    private BiMap<Character, AbstractContour> apcirclesBridge = HashBiMap.create();

    public AbstractDiagramProvider(JSONArea areaSpec) {
        // Build a set of all the contours mentioned in the input JSON. 
        Set<AbstractContour> contours = areaSpec.area_specifications.stream().flatMap(s -> s.keySet().stream())
                                                                             .map(e -> new AbstractContour(e))
                                                                             .collect(Collectors.toSet());

        diagram = new org.eulerdiagrams.AbstractDiagram.WeightedAbstractDiagram(contours);

        for(Map<String,Double> e : areaSpec.area_specifications) {
            // We know e has one key and one value
            Optional<String> rZoneSpec = e.keySet().stream().findFirst();

            if(!rZoneSpec.isPresent()) {
                throw new IllegalArgumentException();
            }

            double weight = e.get(rZoneSpec.get());
            diagram.addZone(weight, parseWilconsonSyntax(rZoneSpec.get()));
        }
    }

    public AbstractDiagramProvider(org.eulerdiagrams.AbstractDiagram.WeightedAbstractDiagram diagram) {
        this.diagram = diagram;
    }

    public AreaSpecification asAreaSpecification() {
        // We have to translate our internal AbstractDiagram into an
        // apCircles.AbstractDiagram, then into an AreaSpecification.
        org.eulerdiagrams.vennom.apCircles.AbstractDiagram pjrDiagram;

        // To create an apCircles.AbstractDiagram we must map each Contour to a
        // single letter label & write out the zones in the apCircles
        // micro-syntax.

        // First map Characters to each Contour

        // Evil...but a result of the design decision of the underlying library
        final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','o','p','q','r','s','t','u','v','w','x','y','z'};
        AbstractContour [] contours = diagram.getContours().toArray(new AbstractContour[diagram.getContours().size()]);
        for(int i=0; (i < contours.length) && (i < alphabet.length); i++) {
            apcirclesBridge.put(alphabet[i], contours[i]);
        }

        // Then build the Rogers syntax for each zone
        StringBuilder apCirclesSyntax = new StringBuilder();
        Map<AbstractZone, Double> weightmap = diagram.getWeightedZones();
        HashMap<String, Double> pjrWeights = new HashMap<String, Double>();
        for(AbstractZone z: weightmap.keySet()) {
            StringBuilder pjrZone = new StringBuilder();
            for (AbstractContour c: z.getInContours()) {
                pjrZone.append(apcirclesBridge.inverse().get(c));
            }
            pjrWeights.put(pjrZone.toString(), weightmap.get(z));
            apCirclesSyntax.append(pjrZone);
            apCirclesSyntax.append(" "); //separate zones with a space
        }

        // Finally, put the diagram together.
        pjrDiagram = new org.eulerdiagrams.vennom.apCircles.AbstractDiagram(apCirclesSyntax.toString());
        AreaSpecification aspec = new AreaSpecification(pjrDiagram, pjrWeights);
        return aspec;
    }

    //TODO
    // Convert the labes in the Graph back to a VennDiagram
    public VennDiagram asVennDiagram(Graph g) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }
    /**
     * This syntax is too esoteric to warrant a parser generator, hence this
     * hand-rolled excuse.
     */
    public AbstractContour[] parseWilconsonSyntax(String usyntax) {
        // Wilconson syntax is of the form where zones are separated with a space and
        // intersection is denoted with an ampersand.
        List<AbstractContour> contours = new Vector<AbstractContour>();

        String [] contourLabels = usyntax.split("&");
        for(String s: contourLabels) {
            contours.add(new AbstractContour(s));
        }
        return contours.toArray(new AbstractContour[contours.size()]);
    }
}
