import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dividends-received.reducer';
import { IDividendsReceived } from 'app/shared/model/dividends-received.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDividendsReceivedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DividendsReceivedDetail extends React.Component<IDividendsReceivedDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dividendsReceivedEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.dividendsReceived.detail.title">DividendsReceived</Translate> [
            <b>{dividendsReceivedEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="paymentDate">
                <Translate contentKey="accounTalkApp.dividendsReceived.paymentDate">Payment Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={dividendsReceivedEntity.paymentDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="amountReceived">
                <Translate contentKey="accounTalkApp.dividendsReceived.amountReceived">Amount Received</Translate>
              </span>
            </dt>
            <dd>{dividendsReceivedEntity.amountReceived}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.dividendsReceived.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{dividendsReceivedEntity.taxReturn ? dividendsReceivedEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.dividendsReceived.shares">Shares</Translate>
            </dt>
            <dd>{dividendsReceivedEntity.shares ? dividendsReceivedEntity.shares.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/dividends-received" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/dividends-received/${dividendsReceivedEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dividendsReceived }: IRootState) => ({
  dividendsReceivedEntity: dividendsReceived.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DividendsReceivedDetail);
