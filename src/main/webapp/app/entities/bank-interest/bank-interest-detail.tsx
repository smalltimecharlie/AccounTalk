import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bank-interest.reducer';
import { IBankInterest } from 'app/shared/model/bank-interest.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBankInterestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BankInterestDetail extends React.Component<IBankInterestDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bankInterestEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.bankInterest.detail.title">BankInterest</Translate> [<b>{bankInterestEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="netInterest">
                <Translate contentKey="accounTalkApp.bankInterest.netInterest">Net Interest</Translate>
              </span>
            </dt>
            <dd>{bankInterestEntity.netInterest}</dd>
            <dt>
              <span id="taxDeducted">
                <Translate contentKey="accounTalkApp.bankInterest.taxDeducted">Tax Deducted</Translate>
              </span>
            </dt>
            <dd>{bankInterestEntity.taxDeducted}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.bankInterest.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{bankInterestEntity.taxReturn ? bankInterestEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.bankInterest.bankdetails">Bankdetails</Translate>
            </dt>
            <dd>{bankInterestEntity.bankdetails ? bankInterestEntity.bankdetails.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/bank-interest" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/bank-interest/${bankInterestEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ bankInterest }: IRootState) => ({
  bankInterestEntity: bankInterest.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankInterestDetail);
