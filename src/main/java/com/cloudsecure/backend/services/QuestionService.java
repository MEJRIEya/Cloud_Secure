package com.cloudsecure.backend.services;

import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getRandomQuestions(int number) {
        List<Question> all = questionRepository.findAll();
        Collections.shuffle(all);
        return all.stream().limit(number).toList();
    }

    public List<Question> getQuestionsByCategory(String category, int number) {
        List<Question> all = questionRepository.findByCategory(category);
        Collections.shuffle(all);
        return all.stream().limit(number).toList();
    }

    public int seedDefaultQuestions() {
        AtomicInteger inserted = new AtomicInteger(0);

        // 🔐 Gestion des accès
        inserted.addAndGet(insertIfMissing("Votre organisation utilise-t-elle l’authentification multi-facteurs (MFA) pour l’accès au cloud ?", "Gestion des accès", 2));
        inserted.addAndGet(insertIfMissing("Les comptes à privilèges sont-ils régulièrement audités et surveillés ?", "Gestion des accès", 2));
        inserted.addAndGet(insertIfMissing("Un principe du moindre privilège est-il appliqué pour les utilisateurs et services ?", "Gestion des accès", 2));

        // ☁️ Protection des données
        inserted.addAndGet(insertIfMissing("Les données sensibles sont-elles chiffrées au repos (at rest) ?", "Protection des données", 2));
        inserted.addAndGet(insertIfMissing("Les communications réseau entre services cloud sont-elles chiffrées (TLS/SSL) ?", "Protection des données", 2));
        inserted.addAndGet(insertIfMissing("Des politiques de sauvegarde et de restauration sont-elles définies et testées régulièrement ?", "Protection des données", 2));

        // 🛡️ Sécurité réseau
        inserted.addAndGet(insertIfMissing("Un pare-feu ou un WAF (Web Application Firewall) est-il configuré dans votre infrastructure cloud ?", "Sécurité réseau", 2));
        inserted.addAndGet(insertIfMissing("Les accès réseau sont-ils restreints par des règles de sécurité basées sur les IP et ports ?", "Sécurité réseau", 2));
        inserted.addAndGet(insertIfMissing("Un système de détection d’intrusion (IDS/IPS) est-il mis en place pour le cloud ?", "Sécurité réseau", 2));

        // 📊 Conformité & gouvernance
        inserted.addAndGet(insertIfMissing("Votre organisation suit-elle des normes de conformité (ISO 27001, GDPR, SOC2, etc.) pour le cloud ?", "Conformité & gouvernance", 2));
        inserted.addAndGet(insertIfMissing("Un plan de réponse aux incidents cloud est-il documenté et testé régulièrement ?", "Conformité & gouvernance", 2));
        inserted.addAndGet(insertIfMissing("Les journaux (logs) cloud sont-ils collectés, centralisés et analysés ?", "Conformité & gouvernance", 2));

        return inserted.get();
    }

    private int insertIfMissing(String text, String category, int riskWeight) {
        if (questionRepository.existsByText(text)) {
            return 0;
        }
        Question q = new Question();
        q.setText(text);
        q.setCategory(category);
        q.setRiskWeight(riskWeight);
        questionRepository.save(q);
        return 1;
    }
}
