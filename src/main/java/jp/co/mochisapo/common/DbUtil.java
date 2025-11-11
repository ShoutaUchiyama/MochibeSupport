package jp.co.mochisapo.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DB接続取得に関するユーティリティ。
 * <p>
 * JNDI から DataSource を解決し、必要なときに Connection を取得します。
 * 例：
 * <pre>
 * try (Connection conn = DbUtil.getConnection()) {
 *     // DB処理
 * }
 * </pre>
 * </p>
 */
public final class DbUtil {

    /** JNDI ルート（アプリサーバ既定の環境コンテキスト） */
    private static final String JNDI_ENV = "java:/comp/env";
    /** 設定した DataSource 名 */
    private static final String JNDI_DATASOURCE_NAME = "jdbc/mochisapo";

    private DbUtil() {
        // ユーティリティクラスのためインスタンス化させない
    }

    /**
     * DataSource を JNDI から解決します。
     *
     * @return 解決された DataSource
     * @throws NamingException ルックアップに失敗した場合
     */
    public static DataSource getDataSource() throws NamingException {
        Context initial = new InitialContext();
        Context env = (Context) initial.lookup(JNDI_ENV);
        return (DataSource) env.lookup(JNDI_DATASOURCE_NAME);
    }

    /**
     * 新しい DB コネクションを取得
     * <p>取得したコネクションは呼び出し側でクローズすること。</p>
     *
     * @return 新しい Connection
     * @throws SQLException     接続取得に失敗した場合
     * @throws NamingException  DataSource の解決に失敗した場合
     */
    public static Connection getConnection() throws SQLException, NamingException {
        return getDataSource().getConnection();
    }

    /**
     * 例外を投げずに静かにクローズ（フォールバック用）
     * <p>基本は try-with-resources を使い、このメソッドはレガシー互換や
     * ネスト解除が難しい場面でのみ使用すること。</p>
     *
     * @param closeable クローズ可能リソース（Connection/Statement/ResultSet など）
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception ignored) {
            // ログフレームワークがあれば warn などで残す
        }
    }
}

